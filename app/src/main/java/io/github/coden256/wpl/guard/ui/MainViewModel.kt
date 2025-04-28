package io.github.coden256.wpl.guard.ui

// MainViewModel.kt
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedTabIndex by mutableIntStateOf(0)

    val tabs = listOf(
        TabItem("Work Results", Icons.Default.Work),
        TabItem("System Info", Icons.Default.Info),
        TabItem("Apps", Icons.Default.Rule),
        TabItem("Telegram", Icons.Default.Message),
        TabItem("Rethink", Icons.Default.Shield),
        TabItem("Tree", Icons.Default.AccountTree)
    )
}

data class TabItem(
    val title: String,
    val icon: ImageVector
)