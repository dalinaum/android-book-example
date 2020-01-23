package com.example.subclassview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var mTextView: TextView? = null
    private var mEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView = findViewById(R.id.text)
        mEditText = findViewById(R.id.editText)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener { v -> mTextView!!.text = mEditText!!.text.toString() }
    }
}
