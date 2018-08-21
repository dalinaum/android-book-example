package com.example.edittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var textView: TextView? = null
    private var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text)
        editText = findViewById(R.id.editText)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            textView?.text = editText?.text.toString()
        }
    }
}
