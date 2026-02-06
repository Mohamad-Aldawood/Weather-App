package com.example.weatherapp.util

import javax.inject.Inject

class GetLocationManager @Inject constructor(
    private val appLocationManager: AppLocationManager
) : LocationProvider {
    override suspend fun getCurrentLocation(): Pair<Double, Double>? =
        appLocationManager.getCurrentLocation()
}