package com.example.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class PreviewThread extends Thread {

    private final Context context;
    private final TextureView textureView;
    private Size previewSize;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewBuilder;
    private CameraCaptureSession previewSession;
    private CameraDevice.StateCallback stateCallback = new CameraDeviceStateCallback();
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureViewSurfaceTextureListener();
    private Semaphore cameraOpenCloseLock = new Semaphore(1);

    public PreviewThread(Context context, TextureView textureView) {
        this.context = context;
        this.textureView = textureView;
    }

    public void openCamera() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = getBackFacingCameraId(cameraManager);
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewSize = map.getOutputSizes(SurfaceTexture.class)[0];

            int permissionCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
            if(permissionCamera == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MainActivity.REQUEST_CAMERA);
            } else {
                cameraManager.openCamera(cameraId, stateCallback, null);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private String getBackFacingCameraId(CameraManager cameraManager) {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                int cameraOrientation = cameraCharacteristics.get(cameraCharacteristics.LENS_FACING);
                if (cameraOrientation == CameraCharacteristics.LENS_FACING_BACK) {
                    return cameraId;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void startPreview() {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        if (null == texture) {
            return;
        }
        texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        Surface surface = new Surface(texture);
        try {
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        previewBuilder.addTarget(surface);
        try {
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    previewSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        previewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("PreviewThread");
        thread.start();
        Handler backgroundHandler = new Handler(thread.getLooper());
        try {
            previewSession.setRepeatingRequest(previewBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        try {
            cameraOpenCloseLock.acquire();
            if (null != cameraDevice) {
                cameraDevice.close();
                cameraDevice = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cameraOpenCloseLock.release();
        }
    }

    public void onResume() {
        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }

    private class TextureViewSurfaceTextureListener implements TextureView.SurfaceTextureListener {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    }

    private class CameraDeviceStateCallback extends CameraDevice.StateCallback {

        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    }
}
