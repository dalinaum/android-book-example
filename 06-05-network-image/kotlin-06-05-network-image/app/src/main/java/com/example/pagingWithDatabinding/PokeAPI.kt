package com.example.pagingWithDatabinding

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {
    @GET("pokemon/")
    fun listPokemons(): Call<Response>

    @GET("pokemon/")
    fun listPokemons(@Query("offset") offset: String, @Query("limit") limit: String): Call<Response>

    @GET("pokemon/{pid}/")
    fun getPokemon(@Path("pid") pid: Int): Call<PokemonResponse>
}
