package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.Serializable


@Serializable
data class Wind(

    var speed: Double? = null,
    var deg: Int? = null,
    var gust: Double? = null

)