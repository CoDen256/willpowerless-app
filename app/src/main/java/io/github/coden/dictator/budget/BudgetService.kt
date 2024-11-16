package io.github.coden.dictator.budget

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.UserManager
import android.widget.Toast
import io.github.coden.dictator.DictatorAdminReceiver
import io.github.coden.dictator.ResetVpnTimeReceiver
import io.github.coden.dictator.VpnReenableReceiver
import java.time.LocalDateTime
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
        const val WEEKLY_BUDGET_SECONDS = 5 * 60 * 60L // 5 hours
    }

    fun getRemainingBudget(): Long {
        return sharedPrefs.getLong("budget", WEEKLY_BUDGET_SECONDS)
    }

    fun reduceBudget(seconds: Long) {
        val remaining = getRemainingBudget() - seconds
        sharedPrefs.edit().putLong("budget", remaining.coerceAtLeast(0)).apply()
    }

    fun resetWeeklyBudget() {
        sharedPrefs.edit().putLong("budget", WEEKLY_BUDGET_SECONDS).apply()
    }

    fun enableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, packageName, true)
    }

    fun disableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, null, true)
    }

    fun enableVpnAt(time: LocalDateTime) {
        setVpnReenableAlarmAt(context, time)
        Toast.makeText(context, "Set alarm at $time", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setVpnReenableAlarmIn(context: Context, sessionDuration: kotlin.time.Duration) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, VpnReenableReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)

        // Set the alarm for the session duration
        val triggerAtMillis = System.currentTimeMillis() + sessionDuration.inWholeMilliseconds
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setVpnReenableAlarmAt(context: Context, time: LocalDateTime){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, time.hour)
        calendar.set(Calendar.MINUTE, time.minute)
        calendar.set(Calendar.SECOND, time.second)
        calendar.set(Calendar.DAY_OF_YEAR, time.dayOfYear)
        calendar.set(Calendar.YEAR, time.year)

        // Create an Intent to fire when the alarm triggers
        val intent = Intent(context, VpnReenableReceiver::class.java)

        // Create a PendingIntent that will trigger the intent when the alarm goes off
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)

        // Get the AlarmManager system service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set an exact alarm at the specific time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
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