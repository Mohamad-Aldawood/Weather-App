package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.Serializable


@Serializable
data class Weather(

    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null

)