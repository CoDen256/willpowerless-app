package io.github.coden.guard.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.github.coden.guard.core.Owner
import io.github.coden.guard.core.budget.BudgetService

class ResetVpnTimeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pack = "com.celzero.bravedns"
        val service = BudgetService(context, Owner(context), pack)
        Log.i("RESET_ALARM", "REsetting everything")
        service.enableVPN()
        service.removeAlarm()
        service.resetWeeklyBudget()
    }
}