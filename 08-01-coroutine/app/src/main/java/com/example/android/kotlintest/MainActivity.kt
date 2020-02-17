package com.example.android.kotlintest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        lifecycleScope.launch {
//            val deferred1 = fetchDataAsync()
//            val deferred2 = fetchData2Async()
//            val processedData = processData("${deferred1.await()} ${deferred2.await()}")
//            Toast.makeText(this@MainActivity, processedData, Toast.LENGTH_LONG).show()
//        }

        val ceh = CoroutineExceptionHandler { _, exception ->
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show();
        }

        lifecycleScope.launch(ceh) {
            val temperatures = withContext(Dispatchers.IO) {
                getTemperatures()
            }
            for (temperature in temperatures) {
                Log.d("TEST", "$temperature")
            }
        }

//        val channel = Channel<Int>()
//        lifecycleScope.launch {
//            repeat (5) {
//                channel.send(20 + it)
//            }
//            channel.close()
//        }

//        val channel = lifecycleScope.produce<Int> {
////            repeat(5) {
////                channel.send(20 + it)
////            }
////        }
////
////        lifecycleScope.launch {
////            for (temperature in channel) {
////                Log.d("TEST", "$temperature")
////            }
////        }

        val flow: Flow<Int> = flow {
            repeat(5) {
                emit(20 + it)
            }
            throw java.lang.RuntimeException("nice to meet you")
        }.catch { exception ->
            Log.d("TEST", "$exception")
        }.flowOn(Dispatchers.IO)

        lifecycleScope.launch {
            flow.map {
                it * 2
            }.flowOn(Dispatchers.Default)
                .collect { temperature ->
                    Log.d("TEST", "$temperature")
                }
        }
////        flow.asLiveData()

//        lifecycleScope.launch {
//            (20..25).asFlow()
//                .collect { temperature ->
//                    Log.d("TEST", "$temperature")
//                }
//        }
//        val flow = callbackFlow {
//            repeat(5) {
//                offer(20 + it)
//            }
            // cancel(CancellationException("Error)"))
//            close()
//        }

//        lifecycleScope.launch {
//            flow.collect { temperature ->
//                Log.d("TEST", "$temperature")
//            }
//        }

    }
}

fun getTemperatures(): MutableList<Int> {
    val list = mutableListOf<Int>()
    repeat(5) {
        list.add(20 + it)
    }
    throw RuntimeException("Nice to meet you")
    return list
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