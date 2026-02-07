package com.example.weatherapp.data.remote.geo.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoCodingResponse(
    @SerialName("name") val name: String,
    @SerialName("local_names") val local_names: Map<String, String>,  // Map for local names
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("country") val country: String,
    @SerialName("state") val state: String? = null
)
