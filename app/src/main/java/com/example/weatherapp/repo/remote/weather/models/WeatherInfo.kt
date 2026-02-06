package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ForecastItem(
    @SerialName("dt") val dt: Int? = null,
    @SerialName("main") val main: Main? = null,
    @SerialName("weather") val weather: List<Weather> = emptyList(),
    @SerialName("clouds") val clouds: Clouds? = null,
    @SerialName("wind") val wind: Wind? = null,
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("pop") val pop: Double? = null,
    @SerialName("sys") val sys: Sys? = null,
    @SerialName("dt_txt") val dtTxt: String? = null
)