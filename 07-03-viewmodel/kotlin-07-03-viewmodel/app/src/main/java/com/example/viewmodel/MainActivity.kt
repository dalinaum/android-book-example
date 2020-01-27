package com.example.viewmodel

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val pokeViewModel = ViewModelProvider(this).get<PokeViewModel>(PokeViewModel::class.java)
        pokeViewModel.results.observe(this, Observer { results ->
            var text = ""
            if (results != null) {
                for (result in results) {
                    text += result.name + "\n"
                }
                textView.text = text
            }
        })
    }
}
