package com.example.bindingadapter

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.dalinaum.bindingadapter.R
import com.example.dalinaum.bindingadapter.databinding.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel = ViewModel()
        binding.viewModel = viewModel
        viewModel.title.set("Reverse String")
    }

    class ViewModel {
        var title = ObservableField<String>()
    }
}
