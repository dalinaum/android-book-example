package com.example.paging;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeAPI {
    @GET("pokemon/")
    Call<Response> listPokemons();

    @GET("pokemon/")
    Call<Response> listPokemons(@Query("offset") String offset, @Query("limit") String limit);
}
