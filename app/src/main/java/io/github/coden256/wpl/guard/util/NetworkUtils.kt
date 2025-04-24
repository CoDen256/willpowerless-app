package io.github.coden256.wpl.guard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkUtils(private val context: Context) {
    // Connected network: null Unknown, new? false, force? false, test? false
    // Connected network: 441492361229 WiFi, new? true, force? false, test? false
    // Connected network: 450082295821 Cellular, new? false, force? false, test? false
// onNetworkDisconnected: state: z, UnderlyingNetworks(ipv4Net=[], ipv6Net=[], useActive=true, minMtu=1500, isActiveNetworkMetered=false, lastUpdated=819179, dnsServers={})
//    onNetworkConnected: changes: NetworkChanges(routesChanged=false, netChanged=true, mtuChanged=false) for new: UnderlyingNetworks(ipv4Net=[NetworkProperties(network=102, capabilities=[ Transports: WIFI Capabilities: NOT_METERED&INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED&NOT_ROAMING&FOREGROUND&NOT_CONGESTED&NOT_SUSPENDED&NOT_VCN_MANAGED LinkUpBandwidth>=97612Kbps LinkDnBandwidth>=102753Kbps TransportInfo: <SSID: <unknown ssid>, BSSID: 02:00:00:00:00:00, MAC: 02:00:00:00:00:00, Security type: 2, Supplicant state: COMPLETED, Wi-Fi standard: 6, RSSI: -42, Link speed: 1200Mbps, Tx Link speed: 1200Mbps, Max Supported Tx Link speed: 1200Mbps, Rx Link speed: 1200Mbps, Max Supported Rx Link speed: 1200Mbps, Frequency: 5180MHz, Net ID: -1, Metered hint: false, score: 60, CarrierMerged: false, SubscriptionId: -1, IsPrimary: -1> SignalStrength: -42 UnderlyingNetworks: Null], linkProperties={InterfaceName: wlan0 LinkAddresses: [ 192.168.1.141/24,fe80::e51b:ed6:e091:cf90/64,fdc8:63a8:6b2:0:da60:cc0b:392:edd0/64,fdc8:63a8:6b2:0:e469:d7db:854e:1ed6/64 ] DnsAddresses: [ /fdc8:63a8:6b2::1,/192.168.1.1 ] Domains: lan MTU: 0 ServerAddress: /192.168.1.1 TcpBufferSizes: 524288,1048576,8808040,262144,524288,6710886 Routes: [ fe80::/64 -> :: wlan0 mtu 0,fdc8:63a8:6b2::/48 -> fe80::b219:21ff:fe8c:9e5c wlan0 mtu 0,fdc8:63a8:6b2::/64 -> :: wlan0 mtu 0,192.168.1.0/24 -> 0.0.0.0 wlan0 mtu 0,0.0.0.0/0 -> 192.168.1.1 wlan0 mtu 0 ]}, networkType=WiFi, NotMetered?true)], ipv6Net=[], useActive=true, minMtu=1500, isActiveNetworkMetered=false, lastUpdated=572344, dnsServers={/fdc8:63a8:6b2::1=102, /192.168.1.1=102})
//    onNetworkConnected: changes: NetworkChanges(routesChanged=false, netChanged=true, mtuChanged=false) for new: UnderlyingNetworks(ipv4Net=[NetworkProperties(network=105, capabilities=[ Transports: CELLULAR Capabilities: MMS&SUPL&FOTA&CBS&XCAP&INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED&NOT_ROAMING&FOREGROUND&NOT_CONGESTED&NOT_SUSPENDED&MCX&NOT_VCN_MANAGED LinkUpBandwidth>=18000Kbps LinkDnBandwidth>=47000Kbps Specifier: <TelephonyNetworkSpecifier [mSubId = 5]> SubscriptionIds: {5} UnderlyingNetworks: Null], linkProperties={InterfaceName: rmnet_data2 LinkAddresses: [ 10.142.12.13/30,2a00:fbc:e5dc:9ebc:667f:a587:a679:8bc6/64 ] DnsAddresses: [ /61.8.132.52,/8.8.8.8,/2a00:fbc:2200:a999:110:3634:2:1,/2001:4860:4860::8888 ] Domains: null MTU: 1500 TcpBufferSizes: 2097152,6291456,16777216,512000,2097152,8388608 Routes: [ 0.0.0.0/0 -> 10.142.12.14 rmnet_data2 mtu 1500,::/0 -> fe80::f949:8fae:ad60:b5e rmnet_data2 mtu 1500,10.142.12.12/30 -> 0.0.0.0 rmnet_data2 mtu 0,2a00:fbc:e5dc:9ebc::/64 -> :: rmnet_data2 mtu 0 ]}, networkType=Cellular, NotMetered?false)], ipv6Net=[NetworkProperties(network=105, capabilities=[ Transports: CELLULAR Capabilities: MMS&SUPL&FOTA&CBS&XCAP&INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED&NOT_ROAMING&FOREGROUND&NOT_CONGESTED&NOT_SUSPENDED&MCX&NOT_VCN_MANAGED LinkUpBandwidth>=18000Kbps LinkDnBandwidth>=47000Kbps Specifier: <TelephonyNetworkSpecifier [mSubId = 5]> SubscriptionIds: {5} UnderlyingNetworks: Null], linkProperties={InterfaceName: rmnet_data2 LinkAddresses: [ 10.142.12.13/30,2a00:fbc:e5dc:9ebc:667f:a587:a679:8bc6/64 ] DnsAddresses: [ /61.8.132.52,/8.8.8.8,/2a00:fbc:2200:a999:110:3634:2:1,/2001:4860:4860::8888 ] Domains: null MTU: 1500 TcpBufferSizes: 2097152,6291456,16777216,512000,2097152,8388608 Routes: [ 0.0.0.0/0 -> 10.142.12.14 rmnet_data2 mtu 1500,::/0 -> fe80::f949:8fae:ad60:b5e rmnet_data2 mtu 1500,10.142.12.12/30 -> 0.0.0.0 rmnet_data2 mtu 0,2a00:fbc:e5dc:9ebc::/64 -> :: rmnet_data2 mtu 0 ]}, networkType=Cellular, NotMetered?false)], useActive=true, minMtu=1500, isActiveNetworkMetered=true, lastUpdated=950964, dnsServers={/61.8.132.52=105, /8.8.8.8=105, /2a00:fbc:2200:a999:110:3634:2:1=105, /2001:4860:4860::8888=105})
    // Check if device is connected to any network


//    Connected network: 458672230413 WiFi, new? true, force? false, test? false
//    2025-04-24 18:55:08.031  7136-13407 ConnectivityEvents      com.celzero.bravedns                 I  nw: default4? true, default6? false for NetworkProperties(network=106, capabilities=[ Transports: WIFI Capabilities: NOT_METERED&INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED&NOT_ROAMING&FOREGROUND&NOT_CONGESTED&NOT_SUSPENDED&NOT_VCN_MANAGED LinkUpBandwidth>=97612Kbps LinkDnBandwidth>=102753Kbps TransportInfo: <SSID: <unknown ssid>, BSSID: 02:00:00:00:00:00, MAC: 02:00:00:00:00:00, Security type: 2, Supplicant state: COMPLETED, Wi-Fi standard: 6, RSSI: -47, Link speed: 1200Mbps, Tx Link speed: 1200Mbps, Max Supported Tx Link speed: 1200Mbps, Rx Link speed: 1200Mbps, Max Supported Rx Link speed: 1200Mbps, Frequency: 5180MHz, Net ID: -1, Metered hint: false, score: 60, CarrierMerged: false, SubscriptionId: -1, IsPrimary: -1> SignalStrength: -47 UnderlyingNetworks: Null], linkProperties={InterfaceName: wlan0 LinkAddresses: [ 192.168.1.141/24,fe80::e51b:ed6:e091:cf90/64,fdc8:63a8:6b2:0:da60:cc0b:392:edd0/64,fdc8:63a8:6b2:0:e469:d7db:854e:1ed6/64 ] DnsAddresses: [ /fdc8:63a8:6b2::1,/192.168.1.1 ] Domains: lan MTU: 0 ServerAddress: /192.168.1.1 TcpBufferSizes: 524288,1048576,8808040,262144,524288,6710886 Routes: [ fe80::/64 -> :: wlan0 mtu 0,fdc8:63a8:6b2::/48 -> fe80::b219:21ff:fe8c:9e5c wlan0 mtu 0,fdc8:63a8:6b2::/64 -> :: wlan0 mtu 0,192.168.1.0/24 -> 0.0.0.0 wlan0 mtu 0,0.0.0.0/0 -> 192.168.1.1 wlan0 mtu 0 ]}, networkType=WiFi, NotMetered?true)
//    2025-04-24 18:55:08.035  7136-13407 ConnectivityEvents      com.celzero.bravedns                 I  inform network change: 1, all? true, metered? false
//    2025-04-24 18:55:08.037  7136-13407 ConnectivityEvents      com.celzero.bravedns                 I  mtu4: 1500, mtu6: 1500; final mtu: 1500
//    2025-04-24 18:55:08.037  7136-13407 VpnLifecycle            com.celzero.bravedns                 I  Building vpn for v4? true, v6? false
//    2025-04-24 18:55:08.040  7136-13407 VpnLifecycle            com.celzero.bravedns                 I  onNetworkConnected: changes: NetworkChanges(routesChanged=false, netChanged=true, mtuChanged=false) for new: UnderlyingNetworks(ipv4Net=[NetworkProperties(network=106, capabilities=[ Transports: WIFI Capabilities: NOT_METERED&INTERNET&NOT_RESTRICTED&TRUSTED&NOT_VPN&VALIDATED&NOT_ROAMING&FOREGROUND&NOT_CONGESTED&NOT_SUSPENDED&NOT_VCN_MANAGED LinkUpBandwidth>=97612Kbps LinkDnBandwidth>=102753Kbps TransportInfo: <SSID: <unknown ssid>, BSSID: 02:00:00:00:00:00, MAC: 02:00:00:00:00:00, Security type: 2, Supplicant state: COMPLETED, Wi-Fi standard: 6, RSSI: -47, Link speed: 1200Mbps, Tx Link speed: 1200Mbps, Max Supported Tx Link speed: 1200Mbps, Rx Link speed: 1200Mbps, Max Supported Rx Link speed: 1200Mbps, Frequency: 5180MHz, Net ID: -1, Metered hint: false, score: 60, CarrierMerged: false, SubscriptionId: -1, IsPrimary: -1> SignalStrength: -47 UnderlyingNetworks: Null], linkProperties={InterfaceName: wlan0 LinkAddresses: [ 192.168.1.141/24,fe80::e51b:ed6:e091:cf90/64,fdc8:63a8:6b2:0:da60:cc0b:392:edd0/64,fdc8:63a8:6b2:0:e469:d7db:854e:1ed6/64 ] DnsAddresses: [ /fdc8:63a8:6b2::1,/192.168.1.1 ] Domains: lan MTU: 0 ServerAddress: /192.168.1.1 TcpBufferSizes: 524288,1048576,8808040,262144,524288,6710886 Routes: [ fe80::/64 -> :: wlan0 mtu 0,fdc8:63a8:6b2::/48 -> fe80::b219:21ff:fe8c:9e5c wlan0 mtu 0,fdc8:63a8:6b2::/64 -> :: wlan0 mtu 0,192.168.1.0/24 -> 0.0.0.0 wlan0 mtu 0,0.0.0.0/0 -> 192.168.1.1 wlan0 mtu 0 ]}, networkType=WiFi, NotMetered?true)], ipv6Net=[], useActive=true, minMtu=1500, isActiveNetworkMetered=false, lastUpdated=1021406, dnsServers={/fdc8:63a8:6b2::1=106, /192.168.1.1=106})
//    2025-04-24 18:55:08.043  7136-13407 VpnLifecycle            com.celzero.bravedns                 I
//    inform network change: 0, all? true, metered? true
//    2025-04-24 18:54:30.319  7136-13407 VpnLifecycle            com.celzero.bravedns                 I  onNetworkDisconnected: state: z, UnderlyingNetworks(ipv4Net=[], ipv6Net=[], useActive=true, minMtu=1500, isActiveNetworkMetered=false, lastUpdated=983688, dnsServers={})
//    2025-04-24 18:54:30.323  7136-13407 VpnLifecycle            com.celzero.bravedns                 I
//
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