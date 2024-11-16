package io.github.coden.dictator.budget

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.UserManager
import android.widget.Toast
import io.github.coden.dictator.DictatorAdminReceiver
import io.github.coden.dictator.ResetVpnTimeReceiver
import io.github.coden.dictator.VpnReenableReceiver
import io.github.coden.dictator.VpnTrackingService
import java.util.Calendar

class BudgetService(
    private val context: Context,
    private val packageName: String
) {
    private val sharedPrefs = context.getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
    private val devicePolicyManager = context.getSystemService(DevicePolicyManager::class.java)
    private val adminComponent = ComponentName(context, DictatorAdminReceiver::class.java)

    init {
        devicePolicyManager.clearUserRestriction(adminComponent, UserManager.DISALLOW_APPS_CONTROL)
        devicePolicyManager.addUserRestriction(adminComponent, UserManager.DISALLOW_CONFIG_VPN)
        devicePolicyManager.setApplicationHidden(adminComponent, packageName, false)
        devicePolicyManager.setUninstallBlocked(adminComponent, packageName, false)
    }

    companion object {
        const val WEEKLY_BUDGET_SECONDS = 5 * 60 * 60 // 5 hours
    }

    fun getRemainingBudget(): Int {
        return sharedPrefs.getInt("remaining_budget_seconds", WEEKLY_BUDGET_SECONDS)
    }

    fun reduceBudget(seconds: Int) {
        val remaining = getRemainingBudget() - seconds
        sharedPrefs.edit().putInt("remaining_budget_seconds", remaining.coerceAtLeast(0)).apply()
    }

    fun resetWeeklyBudget() {
        sharedPrefs.edit().putInt("remaining_budget_seconds", WEEKLY_BUDGET_SECONDS).apply()
    }

    fun enableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, packageName, true)
    }

    fun disableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, null, true)
    }

    fun startTimer(duration: Int) {
        Toast.makeText(context, "Set for $duration seconds", Toast.LENGTH_SHORT).show()
        setVpnReenableAlarm(context, duration)
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setVpnReenableAlarm(context: Context, sessionDuration: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, VpnReenableReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)

        // Set the alarm for the session duration
        val triggerAtMillis = System.currentTimeMillis() + (sessionDuration * 1000L)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }


    fun setWeeklyVpnResetAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ResetVpnTimeReceiver::class.java)

        // Create a PendingIntent for the BroadcastReceiver
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Get the current time to calculate the next Monday at 8:00 AM
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8) // Set time to 8:00 AM
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // If today is Monday but the time has already passed, schedule for the next Monday
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1) // Move to the next Monday
        }

        // Schedule the alarm to repeat weekly
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7, // Repeat weekly
            pendingIntent
        )
    }

    fun isVpnEnabled(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networks: Array<Network> = connectivityManager.allNetworks
        for (network in networks) {
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                return true // VPN is enabled
            }
        }
        return false // VPN is not enabled
    }
}