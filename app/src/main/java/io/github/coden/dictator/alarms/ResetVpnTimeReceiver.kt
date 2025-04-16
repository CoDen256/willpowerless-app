package io.github.coden.guard.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.github.coden.guard.Owner
import io.github.coden.guard.budget.BudgetService

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