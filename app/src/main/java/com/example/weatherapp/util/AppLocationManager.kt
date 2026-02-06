package com.example.weatherapp.util

import android.content.Context
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AppLocationManager(context: Context) {

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    suspend fun getCurrentLocation(): Pair<Double, Double>? =
        suspendCancellableCoroutine { cont ->
            try {
                fusedClient.getCurrentLocation(
                    CurrentLocationRequest
                        .Builder()
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build(), null
                )
                    .addOnSuccessListener { loc ->
                        if (loc != null)
                            cont.resume(loc.latitude to loc.longitude)
                        else
                            cont.resume(null)
                    }
                    .addOnFailureListener { cont.resume(null) }
            } catch (e: SecurityException) {
                cont.resumeWithException(e)
            }
        }
}
