package com.example.simpleshare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                new ImageAsyncTask().execute();
            } else {
                String[] permissions = {
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                };
                int requestCode = 200;
                requestPermissions(permissions, requestCode);
            }
        } else {
            new ImageAsyncTask().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void copyImageFromAsset() {
        String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "cat.jpg";

        if (new File(imagePath).exists()) {
            return;
        }

        AssetManager assetManager = getAssets();
        try {
            InputStream in = assetManager.open("cat.jpg");
            OutputStream out = new FileOutputStream(imagePath);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            out.write(buffer);
            in.close();
            out.flush();
            out.close();

            MediaScannerConnection.scanFile(this, new String[]{imagePath}, new String[]{"image/jpeg"}, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            copyImageFromAsset();
            InputStream input = null;
            try {
                input = getAssets().open("cat.jpg");
                if (input != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    input.close();
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
            Log.d("AAAA", "shareActionProvider" + shareActionProvider);
            if (shareActionProvider != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "cat.jpg";
                Uri assetUri = Uri.fromFile(new File(imagePath));
                shareIntent.putExtra(Intent.EXTRA_STREAM, assetUri);
                shareActionProvider.setShareIntent(shareIntent);
                Log.d("AAAA", "shareActionProvider set!");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new ImageAsyncTask().execute();
        }
    }
}