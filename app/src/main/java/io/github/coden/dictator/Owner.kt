package io.github.coden.dictator

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.UserManager
import android.util.Log
import android.widget.Toast

class Owner(private val context: Context) {
    private val devicePolicyManager = context.getSystemService(DevicePolicyManager::class.java)
    private val adminComponent = ComponentName(context, DictatorAdminReceiver::class.java)
    val isAdmin: Boolean = devicePolicyManager.isDeviceOwnerApp(context.packageName)

    init {
        verify("init")
    }

    fun priveleged(action: Owner.()->(Unit)): DevicePolicyManager?{
        if (isAdmin){
            action(this)
            return devicePolicyManager
        }else {
            Log.i("Dictator", "Not an admin, operation ignored.")
            return null
        }
    }

    fun clearUserRestriction(key: String){
        verify("clearUserRestriction($key)")
        devicePolicyManager.clearUserRestriction(adminComponent, UserManager.DISALLOW_CONFIG_VPN)
    }

    fun hide(pkg: String, hide: Boolean=true){
        devicePolicyManager.setApplicationHidden(adminComponent, pkg, hide)
        Log.i("DictatorOwner", "$pkg hidden?: $hide")
    }

    fun blockUninstall(pkg: String, block: Boolean=true){
        devicePolicyManager.setUninstallBlocked(adminComponent, pkg, block)
    }

    fun enableBackupService(enable:Boolean=true){
        devicePolicyManager.setBackupServiceEnabled(adminComponent, enable)
    }

    fun forceVpn(pkg: String){
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, pkg, true)
    }

    fun freeVpnForce(){
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, null, true)
    }
    fun verify(op: String = "unspecified"){
        if (!isAdmin){
            Log.i("Dictator", "Not an admin, operation denied: <$op>")
            Toast.makeText(context, "Not an admin, sorry :(", Toast.LENGTH_LONG).show()
            throw IllegalStateException("The ${context.packageName} is not an admin")
        }
    }

    companion object {
        fun asOwner(ctx: Context, action: Owner.()->(Unit)): DevicePolicyManager?{
            return Owner(ctx).priveleged(action)
        }
    }
}