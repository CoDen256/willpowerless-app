package io.github.coden256.wpl.guard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkUtils(private val context: Context) {

    // Check if device is connected to any network
    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkConnectionPostAndroidM(connectivityManager)
        } else {
            checkConnectionPreAndroidM(connectivityManager)
        }
    }

    // Get current WiFi SSID (network name)
    fun getCurrentWifiName(): String? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo
        
        return if (connectionInfo.ssid != "<unknown ssid>") {
            connectionInfo.ssid.removeSurrounding("\"")
        } else {
            null
        }
    }

    // Check if mobile data is currently being used
    fun isUsingMobileData(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
        } else {
            @Suppress("DEPRECATION")
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork?.type == ConnectivityManager.TYPE_MOBILE
        }
        // connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
        // (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).isWifiEnabled = false

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkConnectionPostAndroidM(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @Suppress("DEPRECATION")
    private fun checkConnectionPreAndroidM(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}