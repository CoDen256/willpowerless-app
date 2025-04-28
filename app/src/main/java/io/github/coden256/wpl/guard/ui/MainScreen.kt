package io.github.coden256.wpl.guard.ui

import WorkResultsScreen
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val workViewModel: WorkResultsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val application = LocalContext.current.applicationContext as Application
            return WorkResultsViewModel(application) as T
        }
    })
    val systemViewModel: SystemInfoViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val application = LocalContext.current.applicationContext as Application
            return SystemInfoViewModel(application) as T
        }
    })

    Scaffold(
        topBar = {
            Column {
                TabRow(selectedTabIndex = mainViewModel.selectedTabIndex) {
                    mainViewModel.tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = mainViewModel.selectedTabIndex == index,
                            onClick = { mainViewModel.selectedTabIndex = index },
                            text = { Text(tab.title) },
                            icon = { Icon(tab.icon, contentDescription = tab.title) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when(mainViewModel.selectedTabIndex) {
                0 -> WorkResultsScreen(workViewModel)
                1 -> SystemInfoScreen(systemViewModel)
            }
        }
    }
}