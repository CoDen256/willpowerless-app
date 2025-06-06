
package io.github.coden256.wpl.guard.ui
// MainScreen.kt
import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.coden256.wpl.guard.ui.info.SystemInfoScreen
import io.github.coden256.wpl.guard.ui.info.SystemInfoViewModel
import io.github.coden256.wpl.guard.ui.rule.AppRulesViewModel
import io.github.coden256.wpl.guard.ui.rule.BraveRulesViewModel
import io.github.coden256.wpl.guard.ui.rule.RulesScreen
import io.github.coden256.wpl.guard.ui.rule.RulesViewModel
import io.github.coden256.wpl.guard.ui.rule.TelegramRulesViewModel
import io.github.coden256.wpl.guard.ui.tree.JsonViewModel
import io.github.coden256.wpl.guard.ui.tree.JsonViewerScreen
import io.github.coden256.wpl.guard.ui.work.WorkResultsScreen
import io.github.coden256.wpl.guard.ui.work.WorkResultsViewModel

@Composable
fun MainScreen(
    workViewModel: WorkResultsViewModel = viewModel(),
    systemViewModel: SystemInfoViewModel = viewModel(),
    appRulesViewModel: AppRulesViewModel = viewModel(),
    telRulesViewModel: TelegramRulesViewModel = viewModel(),
    vpnRulesViewModel: BraveRulesViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
    jsonViewModel: JsonViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            Column(Modifier.padding(top = 15.dp)) {
                SecondaryTabRow(selectedTabIndex = mainViewModel.selectedTabIndex) {
                    mainViewModel.tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = mainViewModel.selectedTabIndex == index,
                            onClick = { mainViewModel.selectedTabIndex = index },
                            text = { Text("") },
                            icon = { Icon(tab.icon, contentDescription = "") }
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
                2 -> RulesScreen(appRulesViewModel)
                3 -> RulesScreen(telRulesViewModel)
                4 -> RulesScreen(vpnRulesViewModel)
                5 -> JsonViewerScreen(jsonViewModel)
            }
        }
    }
}