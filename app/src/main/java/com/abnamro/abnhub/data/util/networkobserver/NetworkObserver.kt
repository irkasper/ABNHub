package com.abnamro.abnhub.data.util.networkobserver

import android.net.Network

interface NetworkObserver {
    fun register(onAvailable: (network: Network) -> Unit)
}
