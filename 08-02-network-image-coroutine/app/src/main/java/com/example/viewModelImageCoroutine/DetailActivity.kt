package com.example.viewModelImageCoroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pagingWithDatabinding.R
import com.example.pagingWithDatabinding.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            viewModelScope.launch {
                val pokemon = withContext(Dispatchers.IO) {
                    Repository.getPokemon(pid)
                }
                response.value = pokemon
            }
        }
    }
}