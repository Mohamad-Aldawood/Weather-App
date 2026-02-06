package com.example.weatherapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkUtils @Inject constructor(@param:ApplicationContext private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkState: Flow<NetworkState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                trySend(getState())
            }

            override fun onLost(network: Network) {
                trySend(NetworkState.Unavailable)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                trySend(getState())
            }
        }

        val request = android.net.NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Emit initial state
        trySend(getState())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    fun isInternetAvailable(): Boolean = getState() is NetworkState.Available

    private fun getState(): NetworkState {
        val network = connectivityManager.activeNetwork ?: return NetworkState.Unavailable
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkState.Unavailable

        val hasInternet =
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

        return if (hasInternet) {
            NetworkState.Available
        } else {
            NetworkState.Unavailable
        }
    }

}

sealed class NetworkState {
    object Available : NetworkState()
    object Unavailable : NetworkState()
}