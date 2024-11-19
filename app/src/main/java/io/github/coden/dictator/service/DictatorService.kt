package io.github.coden.dictator.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DictatorService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("DictatorService", "Dictator Service started")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("DictatorService", "Dictator Service received new connection")
        return DictatorBinder
    }
}