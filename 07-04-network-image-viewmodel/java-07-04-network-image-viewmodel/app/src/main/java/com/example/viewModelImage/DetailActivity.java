package com.example.viewModelImage;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewModelImage.databinding.ActivityDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 1);
        viewModel.getPokemon(pid);
    }

    public static class ViewModel extends androidx.lifecycle.ViewModel {
        public MutableLiveData<PokemonResponse> response = new MutableLiveData<>();

        public void getPokemon(int pid) {
            Repository.getInstance().getPokemon(pid).enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                    PokemonResponse pokemonResponse = response.body();
                    ViewModel.this.response.setValue(pokemonResponse);
                }

                @Override
                public void onFailure(Call<PokemonResponse> call, Throwable t) {
                }
            });
        }
    }
}
