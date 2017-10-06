package com.example.glideexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image_view);
        GlideApp.with(this)
                .load("https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png")
                .centerCrop()
                .into(imageView);
    }
}
