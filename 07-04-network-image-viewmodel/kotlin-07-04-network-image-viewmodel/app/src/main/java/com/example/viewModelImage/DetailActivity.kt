package com.example.viewModelImage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.pagingWithDatabinding.R
import com.example.pagingWithDatabinding.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        val viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val intent = intent
        val pid = intent.getIntExtra("pid", 1)
        viewModel.getPokemon(pid)
    }

    class ViewModel : androidx.lifecycle.ViewModel() {
        var response = MutableLiveData<PokemonResponse>()

        fun getPokemon(pid: Int) {
            Repository.getPokemon(pid).enqueue(object : Callback<PokemonResponse?> {
                override fun onResponse(call: Call<PokemonResponse?>, response: Response<PokemonResponse?>) {
                    val pokemonResponse = response.body()
                    this@ViewModel.response.value = pokemonResponse
                }

                override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {}
            })
        }
    }
}