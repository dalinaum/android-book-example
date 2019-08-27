package com.example.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.view.TextureView

class MainActivity : AppCompatActivity() {
    private var textureView: TextureView? = null
    private var previewThread: PreviewThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById(R.id.texture_view)
        previewThread = PreviewThread(this, textureView!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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

    override fun onResume() {
        super.onResume()
        previewThread?.onResume()
    }

    override fun onPause() {
        super.onPause()
        previewThread?.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA -> for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.CAMERA) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        textureView = findViewById(R.id.texture_view)
                        previewThread = PreviewThread(this, textureView!!)
                        previewThread!!.openCamera()
                    } else {
                        finish()
                    }
                }
            }
        }
    }

    companion object {

        val REQUEST_CAMERA = 1
    }
}
