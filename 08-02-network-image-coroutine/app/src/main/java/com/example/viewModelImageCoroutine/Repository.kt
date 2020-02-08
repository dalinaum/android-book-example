package com.example.viewModelImageCoroutine

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    private val pokeAPI: PokeAPI

    init {
        val gson: Gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        val retrofit: Retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        pokeAPI = retrofit.create(PokeAPI::class.java)
    }

    fun listPokemons(): retrofit2.Call<Response> {
        return pokeAPI.listPokemons()
    }

    fun listPokemons(offset: String?, limit: String?): retrofit2.Call<Response> {
        return pokeAPI.listPokemons(offset!!, limit!!)
    }

    suspend fun getPokemon(pid: Int): PokemonResponse {
        return pokeAPI.getPokemon(pid)
    }
}