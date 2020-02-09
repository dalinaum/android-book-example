package com.example.android.flowlivedata

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModel : androidx.lifecycle.ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://worldtimeapi.org/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val worldTimeApi =
        retrofit.create(WorldTimeApi::class.java)

    val seoulTime = flow {
        while (true) {
            emit(worldTimeApi.getSeoulTime())
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)
        .asLiveData()
}