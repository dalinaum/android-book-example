package com.example.android.flowlivedata

import retrofit2.http.GET

interface WorldTimeApi {

    @GET("timezone/Asia/Seoul")
    suspend fun getSeoulTime(): Result
}