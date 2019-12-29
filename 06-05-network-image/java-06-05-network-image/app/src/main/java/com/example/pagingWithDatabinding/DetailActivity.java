package com.example.pagingWithDatabinding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.example.pagingWithDatabinding.databinding.ActivityDetailBinding;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ViewModel viewModel = new ViewModel();
        binding.setViewModel(viewModel);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        PokeAPI pokeAPI = retrofit.create(PokeAPI.class);

        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 1);
        pokeAPI.getPokemon(pid).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                PokemonResponse pokemonResponse = response.body();
                viewModel.response.set(pokemonResponse);
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
            }
        });
    }

    public static class ViewModel {
        public ObservableField<PokemonResponse> response = new ObservableField<>();
    }
}
