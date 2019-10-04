package com.example.simpleshare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
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

        try {
            Log.d("TEST", getFilesDir().getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imagePath = getFilesDir() + File.separator + "cat.jpg";
        getCacheDir();

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ImageAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            copyImageFromAsset();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("AAAA", "shareActionProvider" + shareActionProvider);
            if (shareActionProvider != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                String imagePath = getFilesDir() + File.separator + "cat.jpg";
                File imageFile = new File(imagePath);
                Uri assetUri = FileProvider.getUriForFile(MainActivity.this, "com.example.simpleshare.fileprovider", imageFile);
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