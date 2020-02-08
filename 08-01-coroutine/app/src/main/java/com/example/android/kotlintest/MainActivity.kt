package com.example.android.kotlintest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val deferred1 = fetchDataAsync()
            val deferred2 = fetchData2Async()
            val processedData = processData("${deferred1.await()} ${deferred2.await()}")
            Toast.makeText(this@MainActivity, processedData, Toast.LENGTH_LONG).show()
        }
    }

    fun CoroutineScope.fetchDataAsync() = async(Dispatchers.IO) {
        delay(1000)
        "something"
    }

    fun CoroutineScope.fetchData2Async() = async(Dispatchers.IO) {
        delay(1000)
        "good"
    }

    suspend fun processData(data: String) = withContext(Dispatchers.IO) {
        delay(1000)
        data.toUpperCase()
    }
}
