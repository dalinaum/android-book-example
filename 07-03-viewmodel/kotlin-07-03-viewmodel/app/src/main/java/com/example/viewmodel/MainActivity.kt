package com.example.viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        val pokeViewModel = ViewModelProviders.of(this).get<PokeViewModel>(PokeViewModel::class.java)
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
