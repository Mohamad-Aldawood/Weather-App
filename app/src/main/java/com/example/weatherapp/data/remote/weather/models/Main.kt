package com.example.weatherapp.data.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Main(

    var temp: Double? = null,
    @SerialName("feels_like") var feelsLike: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
    @SerialName("temp_min") var tempMin: Double? = null,
    @SerialName("temp_max") var tempMax: Double? = null,
    @SerialName("sea_level") var seaLevel: Int? = null,
    @SerialName("grnd_level") var grndLevel: Int? = null,
    @SerialName("temp_kf") var tempKf: Double? = null

)