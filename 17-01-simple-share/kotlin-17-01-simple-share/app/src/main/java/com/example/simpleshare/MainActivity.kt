package com.example.simpleshare

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.net.Uri
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
import java.io.InputStream
import java.io.OutputStream

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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
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

        try {
            Log.d("TEST", filesDir.canonicalPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val imagePath = filesDir.toString() + File.separator + "cat.jpg"
        cacheDir

        if (File(imagePath).exists()) {
            return
        }

        val assetManager = assets
        try {
            val `in` = assetManager.open("cat.jpg")
            val out = FileOutputStream(imagePath)
            val buffer = ByteArray(`in`.available())
            `in`.read(buffer)
            out.write(buffer)
            `in`.close()
            out.flush()
            out.close()
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
            Log.d("AAAA", "shareActionProvider" + shareActionProvider!!)
            if (shareActionProvider != null) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/jpg"
                val imagePath = filesDir.toString() + File.separator + "cat.jpg"
                val imageFile = File(imagePath)
                val assetUri = FileProvider.getUriForFile(this@MainActivity, "com.example.simpleshare.fileprovider", imageFile)
                shareIntent.putExtra(Intent.EXTRA_STREAM, assetUri)
                shareActionProvider!!.setShareIntent(shareIntent)
                Log.d("AAAA", "shareActionProvider set!")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ImageAsyncTask().execute()
        }
    }
}