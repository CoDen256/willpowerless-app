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
        TabItem( Icons.Default.Work),
        TabItem( Icons.Default.Info),
        TabItem( Icons.Default.Rule),
        TabItem( Icons.Default.Message),
        TabItem( Icons.Default.Shield),
        TabItem( Icons.Default.AccountTree)
    )
}

data class TabItem(

    val icon: ImageVector
)