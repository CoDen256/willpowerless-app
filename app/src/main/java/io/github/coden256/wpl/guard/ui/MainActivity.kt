package io.github.coden256.wpl.guard.ui

import WorkResultsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.coden256.wpl.guard.ui.theme.GuardServiceMonitorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GuardServiceMonitorTheme {
                MainScreen()
            }
        }
    }

}