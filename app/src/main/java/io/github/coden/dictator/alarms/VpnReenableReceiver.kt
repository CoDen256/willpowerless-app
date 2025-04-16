package io.github.coden.guard.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.coden.guard.Owner
import io.github.coden.guard.budget.BudgetService

class VpnReenableReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = BudgetService(context, Owner(context),"com.celzero.bravedns")
        service.enableVPN()
        service.removeAlarm()
    }
}