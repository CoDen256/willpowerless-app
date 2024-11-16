package io.github.coden.dictator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.coden.dictator.budget.BudgetService
import io.github.coden.dictator.ui.BudgetAlarmApp
import io.github.coden.dictator.ui.BudgetApp
import io.github.coden.dictator.ui.theme.DictatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val pack = "com.celzero.bravedns"

        val service = BudgetService(this, pack)
        if (service.getAlarm() != null){
            service.disableVPN()
        }else{
            service.enableVPN()
        }

        setContent {
            DictatorTheme {
                BudgetAlarmApp(service)
            }
        }
    }

}
