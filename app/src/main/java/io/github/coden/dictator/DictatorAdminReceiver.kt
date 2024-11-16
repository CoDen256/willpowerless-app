package io.github.coden.dictator

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

class DictatorAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // Code to execute when Device Admin is enabled
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        // Code to execute when Device Admin is disabled
    }
}