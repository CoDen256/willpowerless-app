package io.github.coden256.wpl.guard.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

// GuardServiceViewModel.kt
class GuardServiceViewModel : ViewModel() {
    val workerItems = mutableStateListOf<WorkerItem>()
    val infoItems = mutableStateListOf<InfoItem>()
    val statusItems = mutableStateListOf<StatusItem>()
    
    init {
        // Initial dummy data
        workerItems.addAll(listOf(
            WorkerItem(
                id = "1",
                type = "Data Fetch",
                status = "Completed",
                time = "10:15 AM",
                result = "Success",
                color = Color(0xFF4CAF50)
            ),
            WorkerItem(
                id = "2",
                type = "Data Sync",
                status = "Failed",
                time = "10:00 AM",
                result = "Network error",
                color = Color(0xFFF44336)
            )
        ))
        
        infoItems.addAll(listOf(
            InfoItem(
                title = "Server Status",
                value = "Online",
                updatedAt = "10:15 AM",
                color = Color(0xFF2196F3)
            ),
            InfoItem(
                title = "Last Data Update",
                value = "15 items",
                updatedAt = "10:15 AM",
                color = Color(0xFF9C27B0)
            )
        ))
        
        statusItems.addAll(listOf(
            StatusItem(
                title = "Service Active",
                value = "Running",
                color = Color(0xFF4CAF50)
            ),
            StatusItem(
                title = "Next Execution",
                value = "10:30 AM",
                color = Color(0xFFFF9800)
            )
        ))
    }
    
    // Call this from your Worker when it completes
    fun addWorkerResult(item: WorkerItem) {
        workerItems.add(0, item) // Add to beginning
        if (workerItems.size > 50) workerItems.removeAt(workerItems.size-1) // Limit history
    }
    
    // Call this when you fetch new information
    fun updateInformation(item: InfoItem) {
        infoItems.find { it.title == item.title }?.let { existing ->
            infoItems.remove(existing)
        }
        infoItems.add(0, item)
    }
}

data class WorkerItem(
    val id: String,
    val type: String,
    val status: String,
    val time: String,
    val result: String,
    val color: Color
)

data class InfoItem(
    val title: String,
    val value: String,
    val updatedAt: String,
    val color: Color
)

data class StatusItem(
    val title: String,
    val value: String,
    val color: Color
)