package com.example.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeViewModel extends ViewModel {
    private MutableLiveData<List<Result>> results;
    private PokeAPI pokeAPI;

    public LiveData<List<Result>> getResults() {
        if (results == null) {
            results = new MutableLiveData<>();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            pokeAPI = retrofit.create(PokeAPI.class);
            loadResults();
        }
        return results;
    }

    private void loadResults() {
        pokeAPI.listPokemons().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                results.setValue(response.body().results);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
            }
        });
    }
}
