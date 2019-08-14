package com.example.intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.Toast

class FirstIntentService : IntentService("FirstIntentService") {
    private var sum: Int = 0

    init {
        sum = 0
    }

    override fun onHandleIntent(intent: Intent?) {
        val number = intent!!.getIntExtra(NUMBER, 0)
        synchronized(this) {
            try {
                Thread.sleep(1000)
            } catch (ignored: InterruptedException) {
            }

        }
        sum += number
        Log.d("IntentService", "Sum: $sum")
    }

    companion object {
        val NUMBER = "NUMBER"
    }
}
