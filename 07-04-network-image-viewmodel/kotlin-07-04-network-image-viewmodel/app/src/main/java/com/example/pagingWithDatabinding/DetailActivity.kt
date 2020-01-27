package com.example.pagingWithDatabinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.pagingWithDatabinding.databinding.ActivityDetailBinding
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        val viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val pokeAPI = retrofit.create(PokeAPI::class.java)

        val intent = intent
        val pid = intent.getIntExtra("pid", 1)
        pokeAPI.getPokemon(pid).enqueue(object : Callback<PokemonResponse?> {
            override fun onResponse(call: Call<PokemonResponse?>, response: Response<PokemonResponse?>) {
                val pokemonResponse = response.body()
                viewModel.response.setValue(pokemonResponse)
            }

            override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {}
        })
    }

    class ViewModel : androidx.lifecycle.ViewModel() {
        var response = MutableLiveData<PokemonResponse>()
    }
}