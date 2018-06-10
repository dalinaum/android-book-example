package com.example.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModel();
        binding.setViewModel(viewModel);
        viewModel.title.set("Hello Android");
    }

//    public void sayHello(View view) {
//        count++;
//        viewModel.title.set("안녕하세요. " + count + "번째 클릭입니다.");
//    }

    public static class ViewModel {
        private int count = 0;

        public ObservableField<String> title = new ObservableField<>();

        public void sayHello() {
            count++;
            title.set("안녕하세요. " + count + "번째 클릭입니다.");
        }
    }
}
