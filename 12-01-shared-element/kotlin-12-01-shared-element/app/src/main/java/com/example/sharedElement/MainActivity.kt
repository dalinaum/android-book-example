package com.example.sharedElement

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageview = findViewById<ImageView>(R.id.imageView)
        imageview.setOnClickListener { v ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, imageview, "transitionImage")
                startActivity(intent, activityOptions.toBundle())
            } else {
                startActivity(intent)
            }
        }
    }
}
