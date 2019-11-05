package com.example.databinding

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import com.example.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by lazy {
        ViewModel()
    }
//    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        viewModel.title.set("Hello Android")
    }

//    fun sayHello(view: View) {
//        count++
//        viewModel.title.set("안녕하세요. " + count + "번째 클릭입니다.")
//    }

    inner class ViewModel {
        private var count = 0

        var title = ObservableField<String>()

        fun sayHello() {
            count++
            title.set("안녕하세요. " + count + "번째 클릭입니다.")
        }
    }
}
