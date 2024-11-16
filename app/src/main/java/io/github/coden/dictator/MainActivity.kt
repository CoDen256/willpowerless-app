package io.github.coden.dictator

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.UserManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.coden.dictator.ui.theme.DictatorTheme

class MainActivity : ComponentActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DictatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        // Check if the app is set as Device Owner
        if (devicePolicyManager.isDeviceOwnerApp(packageName)) {
            Toast.makeText(this, "App is a device owner, success", Toast.LENGTH_SHORT).show()
            preventUninstall("com.celzero.bravedns")
        } else {
            Toast.makeText(this, "App is not Device Owner", Toast.LENGTH_SHORT).show()
        }
    }

    private fun preventUninstall(packageName: String) {
        // Enable the uninstall block policy for the app
//        devicePolicyManager.setUsbDataSignalingEnabled()
//        devicePolicyManager.setApplicationRestrictions()
//        devicePolicyManager.setRecommendedGlobalProxy()
        devicePolicyManager.clearUserRestriction(adminComponent, UserManager.DISALLOW_APPS_CONTROL)
        devicePolicyManager.addUserRestriction(adminComponent, UserManager.DISALLOW_CONFIG_VPN)
        devicePolicyManager.setApplicationHidden(adminComponent, packageName, false)
        devicePolicyManager.setAlwaysOnVpnPackage(adminComponent, packageName, true)
//        devicePolicyManager.enableSystemApp()
//        devicePolicyManager.addUserRestriction(adminComponent, UserManager.DISALLOW_CONFIG_VPN)
        devicePolicyManager.setUninstallBlocked(adminComponent, packageName, true)
        Toast.makeText(this, "$packageName is protected from uninstallation", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DictatorTheme {
        Greeting("Android")
    }
}