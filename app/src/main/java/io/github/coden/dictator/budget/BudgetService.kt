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
import io.github.coden.dictator.Owner
import io.github.coden.dictator.alarms.ResetVpnTimeReceiver
import io.github.coden.dictator.alarms.VpnReenableReceiver
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar

class BudgetService(
     val context: Context,
     val owner: Owner,
    private val packageName: String
) {
    private val sharedPrefs = context.getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    init {
        owner.priveleged {

        } ?: Toast.makeText(context, "Not an admin, sorry :(", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val WEEKLY_BUDGET_SECONDS = 3 * 60 * 60L // 5 hours
    }

    fun isFirstStart(): Boolean{
        return sharedPrefs.getBoolean("firstStart", true)
    }

    fun setFirstStart(firstStart: Boolean) {
        sharedPrefs.edit().putBoolean("firstStart", firstStart).apply()
    }

    fun getRemainingBudget(): Long {
        return sharedPrefs.getLong("budget", WEEKLY_BUDGET_SECONDS)
    }

    fun setRemainingBudget(seconds: Long){
        sharedPrefs.edit().putLong("budget", seconds.coerceAtLeast(0)).apply()
    }

    fun reduceBudget(seconds: Long) {
        val remaining = getRemainingBudget() - seconds
        sharedPrefs.edit().putLong("budget", remaining.coerceAtLeast(0)).apply()
    }

    fun resetWeeklyBudget() {
        sharedPrefs.edit().putLong("budget", WEEKLY_BUDGET_SECONDS).apply()
    }

    fun enableVPN() {
        owner.priveleged { forceVpn(packageName) }
    }

    fun disableVPN() {
        owner.priveleged { freeVpnForce() }
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


        // Get the AlarmManager system service
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set an exact alarm at the specific time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, createAlarmIntent())
        saveAlarm(calendar.timeInMillis)
    }

    fun cancelResetAlarm(){
        alarmManager.cancel(createResetIntent())
    }

    fun setWeeklyVpnResetAlarm(time: LocalTime) {

        val now = LocalDateTime.now()
        var base = now.withHour(time.hour).withSecond(time.second).withMinute(time.minute).with(TemporalAdjusters.next(DayOfWeek.MONDAY))

        // Get the current time to calculate the next Monday at 8:00 AM
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, base.year)
        calendar.set(Calendar.DAY_OF_YEAR, base.dayOfYear)
        calendar.set(Calendar.HOUR_OF_DAY, base.hour) // Set time to 8:00 AM
        calendar.set(Calendar.MINUTE, base.minute)
        calendar.set(Calendar.SECOND,base.second)
        calendar.set(Calendar.MILLISECOND,0)

        // Schedule the alarm to repeat weekly
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7, // Repeat weekly
            createResetIntent()
        )
        Toast.makeText(context, "Set reset alarm at ${Instant.ofEpochMilli(calendar.timeInMillis).atZone(
            ZoneId.of("CET")).toLocalDateTime()}", Toast.LENGTH_SHORT).show()
    }

     fun cancelAlarm(){
        alarmManager.cancel(createAlarmIntent())
        removeAlarm()
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

     fun saveAlarm(timeInMillis: Long) {
        sharedPrefs.edit().putLong("alarm", timeInMillis).apply()
    }

     fun removeAlarm() {
        sharedPrefs.edit().putLong("alarm", -1).apply()
    }

     fun getAlarm(): Long? {
        return sharedPrefs
            .getLong("alarm", -1)
            .let { if(it == -1L) null else it }
    }

    private fun createAlarmIntent(): PendingIntent {
        // Create an Intent to fire when the alarm triggers
        val intent = Intent(context, VpnReenableReceiver::class.java)

        // Create a PendingIntent that will trigger the intent when the alarm goes off
        return PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)
    }


    private fun createResetIntent(): PendingIntent {
        val intent = Intent(context, ResetVpnTimeReceiver::class.java)

        // Create a PendingIntent for the BroadcastReceiver
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        return pendingIntent
    }


}
