package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.Serializable


@Serializable
data class Coord(

    var lat: Double? = null,
    var lon: Double? = null

)