package io.github.coden.dictator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ResetVpnTimeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Reset the accumulated VPN time
        val prefs = context.getSharedPreferences("VPN_DISABLED_TIME", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putLong("accumulated_time", 0) // Reset accumulated time to 0
            apply()
        }
    }
}