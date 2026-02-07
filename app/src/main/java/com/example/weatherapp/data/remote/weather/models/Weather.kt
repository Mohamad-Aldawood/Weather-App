package com.example.weatherapp.data.remote.weather.models

import kotlinx.serialization.Serializable


@Serializable
data class Weather(

    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null

)