package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class FirstReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.broadcast.FIRST_MESSAGE")) {
            Toast.makeText(context, "리시버가 수행되었습니다", Toast.LENGTH_SHORT).show();
        }
    }
}
