package io.github.coden.dictator.budget

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.health.connect.datatypes.units.Length
import android.os.Handler
import android.os.Looper
import android.os.UserManager
import android.widget.Toast
import io.github.coden.dictator.DictatorAdminReceiver

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
        const val WEEKLY_BUDGET_MINUTES = 5 * 60 // 5 hours
    }

    fun getRemainingBudget(): Int {
        return sharedPrefs.getInt("remaining_budget", WEEKLY_BUDGET_MINUTES)
    }

    fun reduceBudget(minutes: Int) {
        val remaining = getRemainingBudget() - minutes
        sharedPrefs.edit().putInt("remaining_budget", remaining.coerceAtLeast(0)).apply()
    }

    fun resetWeeklyBudget() {
        sharedPrefs.edit().putInt("remaining_budget", WEEKLY_BUDGET_MINUTES).apply()
    }

    fun enableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, packageName, true)
    }
    fun disableVPN() {
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, null, true)
    }

    fun startTimer(duration: Int, onFinish: () -> Unit) {
        Toast.makeText(context, "Set for $duration minutes", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed(onFinish, duration * 60 * 1000L)
    }
}