package io.github.coden256.wpl.guard.ui.info

import android.app.Application
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.coden256.wpl.guard.config.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

data class SystemInfo(
    val id: String,
    val title: String,
    val value: String,
    val iconRes: Int? = null
)

class SystemInfoViewModel(application: Application) : AndroidViewModel(application), KoinComponent {
    val systemInfo = MutableStateFlow<List<SystemInfo>>(emptyList())
    val appConfig by inject<AppConfig>()

    init {
        viewModelScope.launch {
            while (true) {
                refreshSystemInfo()
                delay(5000)
            }
        }
    }

    fun refreshSystemInfo() {
        val newInfo = mutableListOf(
            SystemInfo(
                id = "os_version",
                title = "Android Version",
                value = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
            ),
            SystemInfo(
                id = "device_model",
                title = "Device Model",
                value = "${Build.MANUFACTURER} ${Build.MODEL}"
            ),
            SystemInfo(
                id = "screen_resolution",
                title = "Screen Resolution",
                value = getScreenResolution()
            ),
            SystemInfo(
                id = "battery_status",
                title = "Battery Status",
                value = getBatteryStatus()
            ),
            SystemInfo(
                id = "storage_space",
                title = "Storage Space",
                value = getStorageSpace()
            ),
            SystemInfo(
                id = "memory_info",
                title = "Memory Info",
                value = getMemoryInfo()
            ),
            SystemInfo(
                id = "cpu_cores",
                title = "CPU Cores",
                value = "${Runtime.getRuntime().availableProcessors()} cores"
            ),
            SystemInfo(
                id = "architecture",
                title = "CPU Architecture",
                value = Build.SUPPORTED_ABIS.joinToString()
            )
        )
        appConfig.vpnOnPackage?.let {
            newInfo.add(SystemInfo(UUID.randomUUID().toString(), "Always on VPN", it))
        }

        appConfig.hiddenPackages.forEach {
            newInfo.add(SystemInfo(UUID.randomUUID().toString(), it, "hidden"))
        }

        appConfig.unremovablePackages.forEach {
            newInfo.add(SystemInfo(UUID.randomUUID().toString(), it, "unremovable"))
        }

        systemInfo.value = newInfo
    }

    private fun getScreenResolution(): String {
        val displayMetrics = getApplication<Application>().resources.displayMetrics
        return "${displayMetrics.widthPixels}x${displayMetrics.heightPixels} (${displayMetrics.densityDpi}dpi)"
    }

    private fun getBatteryStatus(): String {
        val batteryStatus = getApplication<Application>().registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val batteryPct = if (level != -1 && scale != -1) level * 100 / scale else -1

        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        return when {
            batteryPct != -1 && isCharging -> "$batteryPct% (Charging)"
            batteryPct != -1 -> "$batteryPct%"
            else -> "Unknown"
        }
    }

    private fun getStorageSpace(): String {
        val statFs = StatFs(Environment.getDataDirectory().path)
        val total = statFs.blockCountLong * statFs.blockSizeLong
        val available = statFs.availableBlocksLong * statFs.blockSizeLong
        val used = total - available

        return "Used: ${
            Formatter.formatFileSize(
                getApplication(),
                used
            )
        } / ${Formatter.formatFileSize(getApplication(), total)} (${(used * 100 / total)}%)"
    }

    private fun getMemoryInfo(): String {
        val activityManager =
            getApplication<Application>().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val total = memoryInfo.totalMem
        val available = memoryInfo.availMem
        val used = total - available

        return "Used: ${
            Formatter.formatFileSize(
                getApplication(),
                used
            )
        } / ${Formatter.formatFileSize(getApplication(), total)} (${(used * 100 / total)}%)"
    }
}