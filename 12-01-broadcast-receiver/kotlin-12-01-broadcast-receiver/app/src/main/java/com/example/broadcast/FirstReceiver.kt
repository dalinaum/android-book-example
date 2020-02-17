package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class FirstReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.broadcast.FIRST_MESSAGE") {
            Toast.makeText(context, "리시버가 수행되었습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
