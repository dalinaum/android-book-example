package com.example.simpleshare

import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.FileProvider
import androidx.core.view.MenuItemCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                ImageAsyncTask().execute()
            } else {
                val permissions = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE")
                val requestCode = 200
                requestPermissions(permissions, requestCode)
            }
        } else {
            ImageAsyncTask().execute()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun copyImageFromAsset() {
        val imagePath = filesDir.toString() + File.separator + "cat.jpg"
        if (File(imagePath).exists()) {
            return
        }

        val assetManager = assets
        try {
            val inputStream = assetManager.open("cat.jpg")
            val outputStream = FileOutputStream(imagePath)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            outputStream.write(buffer)
            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    internal inner class ImageAsyncTask : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            copyImageFromAsset()
            return null
        }

        override fun onPostExecute(aVoid: Void) {
            if (shareActionProvider != null) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/jpg"
                val imagePath = filesDir.toString() + File.separator + "cat.jpg"
                val imageFile = File(imagePath)
                val assetUri = FileProvider.getUriForFile(this@MainActivity, "com.example.simpleshare.fileprovider", imageFile)
                shareIntent.putExtra(Intent.EXTRA_STREAM, assetUri)
                shareActionProvider?.setShareIntent(shareIntent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ImageAsyncTask().execute()
        }
    }
}