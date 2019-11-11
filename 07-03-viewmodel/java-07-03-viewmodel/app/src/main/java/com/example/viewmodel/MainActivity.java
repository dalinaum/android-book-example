package com.example.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        PokeViewModel pokeViewModel = ViewModelProviders.of(this).get(PokeViewModel.class);
        pokeViewModel.getResults().observe(this, results -> {
            String text = "";
            for (Result result : results) {
                text += result.name + "\n";
            }
            textView.setText(text);
        });
    }
}
