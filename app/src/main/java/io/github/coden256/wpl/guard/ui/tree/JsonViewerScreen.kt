package io.github.coden256.wpl.guard.ui.tree// JsonViewerScreen.kt
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JsonViewerScreen(
    viewModel: JsonViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val json by viewModel.jsonString.collectAsStateWithLifecycle()
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()) }
    val formattedDate = viewModel.jsonTimestamp.collectAsState()


    Scaffold(
        topBar = {
            Text(
                text = "Last update: "+dateFormat.format(Date(formattedDate.value)),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 15.dp)
            )
        },
        floatingActionButton = {}
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SelectionContainer {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    ScrollableText(
                        text = json,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ScrollableText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle
) {
    val scrollState = rememberScrollState()
    val scrollStateH = rememberScrollState()

    Box(modifier) {
        Text(
            text = text,
            style = style,
            modifier = Modifier
                .verticalScroll(scrollState)
                .horizontalScroll(scrollStateH)
                .fillMaxWidth()
        )
    }
}