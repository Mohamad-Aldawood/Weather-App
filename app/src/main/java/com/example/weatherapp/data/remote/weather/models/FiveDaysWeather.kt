package com.example.weatherapp.data.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FiveDaysWeather(

    var cod: String? = null,
    var message: Int? = null,
    var cnt: Int? = null,
    @SerialName("list") var weatherInfo: List<ForecastItem> = listOf(),
    var city: City? = City()

)