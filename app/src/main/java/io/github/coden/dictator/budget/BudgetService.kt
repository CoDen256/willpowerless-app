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
import android.os.UserManager
import android.widget.Toast
import io.github.coden.dictator.DictatorAdminReceiver
import io.github.coden.dictator.VpnReenableReceiver

class BudgetService(
    private val context: Context,
    private val packageName: String) {
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
}