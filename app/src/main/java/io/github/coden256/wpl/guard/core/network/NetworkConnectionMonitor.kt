package io.github.coden256.wpl.guard.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

class NetworkConnectionMonitor(private val context: Context) {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var isConnected = false
    var onConnectionChanged: ((isConnected: Boolean) -> Unit)? = null

    // Start monitoring network changes
    fun startMonitoring() {
        if (networkCallback != null) return // Already monitoring

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            // Called when the framework connects to a new network
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                checkConnection(network)
            }

            // Called when network capabilities change
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                checkConnection(network)
            }

            // Called when the network is lost
            override fun onLost(network: Network) {
                super.onLost(network)
                updateConnectionStatus(false)
            }
        }

        networkCallback?.let {
            connectivityManager.registerNetworkCallback(networkRequest, it)
        }

        // Check initial connection state
        checkInitialConnection()
    }

    // Stop monitoring network changes
    fun stopMonitoring() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
            networkCallback = null
        }
    }

    private fun checkInitialConnection() {
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
            checkConnection(activeNetwork)
        } else {
            updateConnectionStatus(false)
        }
    }

    private fun checkConnection(network: Network) {
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isInternetAvailable = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        val isValidated =
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true

        updateConnectionStatus(isInternetAvailable && isValidated)
    }

    private fun updateConnectionStatus(newStatus: Boolean) {
        if (isConnected != newStatus) {
            isConnected = newStatus
            onConnectionChanged?.invoke(isConnected)
        }
    }
}