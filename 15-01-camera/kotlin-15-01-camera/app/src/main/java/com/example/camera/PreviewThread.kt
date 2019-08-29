package com.example.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import java.util.concurrent.Semaphore

class PreviewThread(private val context: Context, private val textureView: TextureView) : Thread() {
    private var previewSize: Size? = null
    private var cameraDevice: CameraDevice? = null
    private var previewBuilder: CaptureRequest.Builder? = null
    private var previewSession: CameraCaptureSession? = null
    private val stateCallback = CameraDeviceStateCallback()
    private val surfaceTextureListener = TextureViewSurfaceTextureListener()
    private val cameraOpenCloseLock = Semaphore(1)

    fun openCamera() {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = getBackFacingCameraId(cameraManager)
            val characteristics = cameraManager.getCameraCharacteristics(cameraId!!)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            previewSize = map!!.getOutputSizes<SurfaceTexture>(SurfaceTexture::class.java)[0]

            val permissionCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), MainActivity.REQUEST_CAMERA)
            } else {
                cameraManager.openCamera(cameraId, stateCallback, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun getBackFacingCameraId(cameraManager: CameraManager): String? {
        try {
            for (cameraId in cameraManager.cameraIdList) {
                val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
                val cameraOrientation = cameraCharacteristics.get<Int>(CameraCharacteristics.LENS_FACING)!!
                if (cameraOrientation == CameraCharacteristics.LENS_FACING_BACK) {
                    return cameraId
                }
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        return null
    }

    private fun startPreview() {
        val texture = textureView.surfaceTexture ?: return
        texture.setDefaultBufferSize(previewSize!!.width, previewSize!!.height)
        val surface = Surface(texture)
        try {
            previewBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        previewBuilder?.addTarget(surface)
        try {
            cameraDevice?.createCaptureSession(Arrays.asList(surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    previewSession = session
                    updatePreview()
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {

                }
            }, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun updatePreview() {
        previewBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        val thread = HandlerThread("PreviewThread")
        thread.start()
        val backgroundHandler = Handler(thread.looper)
        try {
            previewSession?.setRepeatingRequest(previewBuilder!!.build(), null, backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun onPause() {
        try {
            cameraOpenCloseLock.acquire()
            if (null != cameraDevice) {
                cameraDevice!!.close()
                cameraDevice = null
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            cameraOpenCloseLock.release()
        }
    }

    fun onResume() {
        textureView.surfaceTextureListener = surfaceTextureListener
    }

    private inner class TextureViewSurfaceTextureListener : TextureView.SurfaceTextureListener {

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        }
    }

    private inner class CameraDeviceStateCallback : CameraDevice.StateCallback() {

        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            startPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {

        }

        override fun onError(camera: CameraDevice, error: Int) {

        }
    }
}
