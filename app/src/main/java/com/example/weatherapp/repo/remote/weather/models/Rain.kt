package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Rain(
    @SerialName("1h") val h1: Double? = null
)
