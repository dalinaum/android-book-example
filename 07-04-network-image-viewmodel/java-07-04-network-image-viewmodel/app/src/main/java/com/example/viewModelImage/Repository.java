package com.example.viewModelImage;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private final PokeAPI pokeAPI;
    private static Repository instance = new Repository();

    private Repository() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        pokeAPI = retrofit.create(PokeAPI.class);
    }

    public static Repository getInstance() {
        return instance;
    }

    Call<Response> listPokemons() {
        return pokeAPI.listPokemons();
    }

    Call<Response> listPokemons(String offset, String limit) {
        return pokeAPI.listPokemons(offset, limit);
    }

    Call<PokemonResponse> getPokemon(int pid) {
        return pokeAPI.getPokemon(pid);
    }
}
