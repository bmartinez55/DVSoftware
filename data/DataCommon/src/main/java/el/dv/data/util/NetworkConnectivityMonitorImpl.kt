package el.dv.data.util

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import el.dv.domain.extension.offerWhenOpen
import el.dv.domain.logging.AppLog
import el.dv.domain.networkmonitor.NetworkConnectivityMonitor
import el.dv.domain.networkmonitor.model.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkConnectivityMonitorImpl(private val connectivityManager: ConnectivityManager) : NetworkConnectivityMonitor {

    @SuppressLint("MissingPermission")
    override suspend fun monitorNetwork(): Flow<NetworkState> = callbackFlow {
        try {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    offerWhenOpen(NetworkState.Connected)
                }
                override fun onLost(network: Network) {
                    when (isNetworkConnected()) {
                        true -> {}
                        false -> offerWhenOpen(NetworkState.Disconnected)
                    }
                }

                override fun onUnavailable() {
                    when (isNetworkConnected()) {
                        true -> {}
                        false -> offerWhenOpen(NetworkState.Disconnected)
                    }
                }
            }
            connectivityManager.registerNetworkCallback(
                NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                    .build(),
                callback
            )

            // Check one time to return network connectivity upon monitoring.
            if (!isNetworkConnected()) {
                offerWhenOpen(NetworkState.Disconnected)
            }
            awaitClose {
                close()
            }
        } catch (e: Exception) {
            AppLog.e(TAG, "monitorNetwork catch error", e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun isNetworkConnected(): Boolean {
        return connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    companion object {
        const val TAG = "NetworkConnectivityMonitorImpl"
    }
}
