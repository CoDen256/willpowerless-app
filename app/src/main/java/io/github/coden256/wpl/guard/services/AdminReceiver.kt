package io.github.coden256.wpl.guard.services

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.i("GuardAdminReceiver", "Thanks for the device owner privileges :)")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Log.i("GuardAdminReceiver", "Noooo... my device owner privileges :(.")
    }
}