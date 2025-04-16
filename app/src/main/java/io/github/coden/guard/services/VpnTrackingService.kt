package io.github.coden.guard.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import io.github.coden.guard.R

class VpnTrackingService : Service() {

    private var startTime: Long = 0
    private var accumulatedTime: Long = 0
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    override fun onCreate() {
        super.onCreate()
        // Initialize accumulated time from SharedPreferences
        accumulatedTime = getSharedPreferences("VPN_DISABLED_TIME", MODE_PRIVATE).getLong("accumulated_time", 0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the VPN tracking when the service is started
        startTime = System.currentTimeMillis() // Record the start time
        startForegroundService() // Start the service in the foreground
        startTrackingTime() // Start tracking the VPN usage time
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the tracking and store accumulated time
        handler.removeCallbacks(runnable!!)
        saveAccumulatedTime()
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, "VPN_TRACKING_CHANNEL")
            .setContentTitle("VPN Tracking")
            .setContentText("Tracking VPN usage time.")
            .setSmallIcon(R.mipmap.ic_launcher) // Replace with your own icon
            .build()

        startForeground(1, notification)
    }

    private fun startTrackingTime() {
        runnable = object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                accumulatedTime += elapsedTime
                startTime = System.currentTimeMillis()

                // Save the accumulated time periodically
                saveAccumulatedTime()

                // Schedule the next update after 1 second
                handler.postDelayed(this, 1000)
            }
        }

        // Start the timer to update every second
        handler.post(runnable!!)
    }

    private fun saveAccumulatedTime() {
        val prefs = getSharedPreferences("VPN_TIME_TRACKING", MODE_PRIVATE)
        with(prefs.edit()) {
            putLong("accumulated_time", accumulatedTime)
            apply()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}