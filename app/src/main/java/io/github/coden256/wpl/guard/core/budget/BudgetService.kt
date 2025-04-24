package io.github.coden256.wpl.guard.core.budget

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.widget.Toast
import io.github.coden256.wpl.guard.core.Owner
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
        if (!owner.isAdmin){
            Toast.makeText(context, "Not an admin, sorry :(", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val WEEKLY_BUDGET_SECONDS = 15 * 60L  // 5 hours
    }

    fun isFirstStart(): Boolean{
        return sharedPrefs.getBoolean("firstStart", true)
    }

    fun setFirstStart(firstStart: Boolean) {
        sharedPrefs.edit().putBoolean("firstStart", firstStart).apply()
    }

    fun getRemainingBudget(): Long {
        return sharedPrefs.getLong("time___)budget", WEEKLY_BUDGET_SECONDS)
    }

    fun setRemainingBudget(seconds: Long){
        sharedPrefs.edit().putLong("time___)budget", seconds.coerceAtLeast(0)).apply()
    }

    fun reduceBudget(seconds: Long) {
        val remaining = getRemainingBudget() - seconds
        sharedPrefs.edit().putLong("time___)budget", remaining.coerceAtLeast(0)).apply()
    }

    fun resetWeeklyBudget() {
        sharedPrefs.edit().putLong("time___)budget", WEEKLY_BUDGET_SECONDS).apply()
    }

    fun enableVPN() {
//        owner.priveleged { removeAlwaysOnVpn() } //forceVpn(packageName)
    }

    fun disableVPN() {
//        owner.priveleged { removeAlwaysOnVpn() }
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
}
