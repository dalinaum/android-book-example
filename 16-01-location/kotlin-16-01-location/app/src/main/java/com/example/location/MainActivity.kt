package com.example.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private var lastLocation: Location? = null
    private var location: TextView? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        location = findViewById(R.id.location)
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

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSIONS)
            } else {
                getLocation()
            }
        } else {
            getLocation()
        }

    }

    private fun getLocation() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        }
        mLocationRequest = LocationRequest()
        mLocationRequest?.interval = 120000 // two minute interval
        mLocationRequest?.fastestInterval = 120000
        mLocationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        mFusedLocationClient?.lastLocation
                ?.addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        return@addOnCompleteListener
                    }

                    lastLocation = task.result
                    if (lastLocation == null) {
                        return@addOnCompleteListener
                    }
                    updateLocation()
                }
    }

    private fun updateLocation() {
        location?.text = "Location latitude:${lastLocation?.latitude}\n\tlongitude:${lastLocation?.longitude}"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSIONS -> if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSIONS = 1
    }
}
