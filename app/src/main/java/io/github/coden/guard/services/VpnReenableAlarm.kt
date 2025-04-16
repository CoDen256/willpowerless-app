package io.github.coden.guard.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.coden.guard.core.Owner
import io.github.coden.guard.core.budget.BudgetService

class VpnReenableAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = BudgetService(context, Owner(context),"com.celzero.bravedns")
        service.enableVPN()
        service.removeAlarm()
    }
}