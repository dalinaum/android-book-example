package com.example.subclassview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text);
        mEditText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> mTextView.setText(mEditText.getText().toString()));
    }
}
