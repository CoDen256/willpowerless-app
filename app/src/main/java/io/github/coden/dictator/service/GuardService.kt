package io.github.coden.dictator.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class GuardService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("GuardService", "Guard Service started")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("GuardService", "Guard Service received a new connection")
        return GuardBinder
    }
}