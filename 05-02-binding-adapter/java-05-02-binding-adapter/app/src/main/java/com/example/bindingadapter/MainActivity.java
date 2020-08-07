package com.example.bindingadapter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.example.dalinaum.bindingadapter.R;
import com.example.dalinaum.bindingadapter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewModel viewModel = new ViewModel();
        binding.setViewModel(viewModel);
        viewModel.title.set("Reverse String");
    }

    public static class ViewModel {
        public ObservableField<String> title = new ObservableField<>();
    }
}
