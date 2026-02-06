package com.example.weatherapp.util

interface LocationProvider {
    suspend fun getCurrentLocation(): Pair<Double, Double>?
}
