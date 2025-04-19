package io.github.coden256.wpl.guard.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_LOCKED_BOOT_COMPLETED
import android.util.Log


class SystemUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i("GuardSystemUpdateReceiver", "Got system update: $intent...")

        when(intent.action){
            ACTION_BOOT_COMPLETED -> onBoot(context)
            ACTION_LOCKED_BOOT_COMPLETED -> onLockedBoot(context)
        }
    }

    private fun onLockedBoot(context: Context){
        Log.i("GuardSystemUpdateReceiver", "Locked boot complete")
    }

    private fun onBoot(context: Context){
        Log.i("GuardSystemUpdateReceiver", "Boot complete")
    }

}