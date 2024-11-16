package io.github.coden.dictator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.coden.dictator.budget.BudgetService

class VpnReenableReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = BudgetService(context, "com.celzero.bravedns")
        service.enableVPN()
        service.removeAlarm()
    }
}