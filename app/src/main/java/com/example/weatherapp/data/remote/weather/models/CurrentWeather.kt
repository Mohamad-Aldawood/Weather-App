package com.example.weatherapp.data.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrentWeather(
    @SerialName("coord") var coord: Coord? = Coord(),
    @SerialName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerialName("base") var base: String? = null,
    @SerialName("main") var main: Main? = Main(),
    @SerialName("visibility") var visibility: Int? = null,
    @SerialName("wind") var wind: Wind? = Wind(),
    @SerialName("rain") var rain: Rain? = Rain(),
    @SerialName("clouds") var clouds: Clouds? = Clouds(),
    @SerialName("dt") var dt: Int? = null,
    @SerialName("sys") var sys: Sys? = Sys(),
    @SerialName("timezone") var timezone: Int? = null,
    @SerialName("id") var id: Int? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("cod") var cod: Int? = null
)