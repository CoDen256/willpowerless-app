package io.github.coden.dictator

import android.annotation.SuppressLint
import android.app.admin.DeviceAdminReceiver
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DictatorPackageReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("DictatorPackageReceiver", "Got broadcast: $intent...")

        val data = intent?.data ?: return
        val packageName = data.schemeSpecificPart
        Toast.makeText(context, "Added $packageName, verifying...", Toast.LENGTH_LONG).show()
        Log.i("DictatorPackageReceiver", "$packageName added, verifying...")
    }
}