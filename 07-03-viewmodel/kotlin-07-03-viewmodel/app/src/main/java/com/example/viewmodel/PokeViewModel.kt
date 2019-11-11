package com.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeViewModel : ViewModel() {
    val results: MutableLiveData<List<Result>> by lazy {
        var liveData = MutableLiveData<List<Result>>()
        loadResults()
        liveData
    }
    private val pokeAPI: PokeAPI by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(PokeAPI::class.java)
    }

    private fun loadResults() {
        pokeAPI.listPokemons().enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                results.value = response.body()?.results
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {}
        })
    }
}
