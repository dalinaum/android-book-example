package com.example.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class FirstIntentService extends IntentService {
    public static final String NUMBER = "NUMBER";
    private int sum;

    public FirstIntentService() {
        super("FirstIntentService");
        sum = 0;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int number = intent.getIntExtra(NUMBER, 0);
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException ignored) {
            }
        }
        sum += number;
        Log.d("IntentService", "Sum: " + sum);
    }
}
