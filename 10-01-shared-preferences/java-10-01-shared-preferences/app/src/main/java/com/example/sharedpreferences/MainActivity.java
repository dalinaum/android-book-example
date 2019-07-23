package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SharedPreferences sharedPreferences;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        sharedPreferences = getSharedPreferences("com.example.sharedpreferenes.PREFERENCE_FILE_KEY", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        counter = sharedPreferences.getInt("COUNTER", counter);
        textView.setText("Counter: " + counter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        counter++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("COUNTER", counter);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
