package com.abnamro.abnhub.data.util.networkobserver

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkObserverImpl(
    private val context: Context
) : NetworkObserver {

    override fun register(onAvailable: (network: Network) -> Unit) {

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                onAvailable(network)
            }
        }

        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}
