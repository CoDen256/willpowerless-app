package io.github.coden.guard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.coden.guard.budget.BudgetService
import java.time.Instant

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pack = "com.celzero.bravedns"
        val service = BudgetService(context, Owner(context), pack)
        val a = service.getAlarm()
        if (a != null){
            if (Instant.ofEpochMilli(a).isAfter(Instant.now())){
                service.disableVPN()
            }
        }else{
            service.enableVPN()
        }
    }

}