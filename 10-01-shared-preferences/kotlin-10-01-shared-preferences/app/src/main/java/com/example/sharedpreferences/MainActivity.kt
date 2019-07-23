package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text_view)
        sharedPreferences = getSharedPreferences("com.example.sharedpreferenes.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        counter = sharedPreferences.getInt("COUNTER", counter)
        textView.text = "Counter: $counter"
    }

    override fun onPause() {
        super.onPause()
        counter++
        val editor = sharedPreferences.edit()
        editor.putInt("COUNTER", counter)
        editor.commit()
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
}
