package com.example.viewmodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        PokeViewModel pokeViewModel = new ViewModelProvider(this).get(PokeViewModel.class);
        pokeViewModel.getResults().observe(this, results -> {
            String text = "";
            for (Result result : results) {
                text += result.name + "\n";
            }
            textView.setText(text);
        });
    }
}
