package com.example.android.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        GlobalScope.launch(Dispatchers.Default) {
//            delay(1000)
//            Log.d("AAA", "Hello World!")
//        }

        MainScope().launch {
            val fetchedData = fetchData()
            val processedData = processData(fetchedData)
            Log.d("AAA", processedData)
        }
    }

    suspend fun fetchData() = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        "something"
    }

    suspend fun processData(data: String) = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        data.toUpperCase()
    }


    suspend fun hello() {
        delay(1000)
        Log.d("AAA", "Hello World!")
        delay(1000)
    }
}
