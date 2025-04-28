
package io.github.coden256.wpl.guard.ui
// MainScreen.kt
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(
    workViewModel: WorkResultsViewModel = viewModel(factory = ViewModelFactory(LocalContext.current.applicationContext as Application)),
    systemViewModel: SystemInfoViewModel = viewModel(factory = ViewModelFactory(LocalContext.current.applicationContext as Application)),
    mainViewModel: MainViewModel = viewModel()
) {
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