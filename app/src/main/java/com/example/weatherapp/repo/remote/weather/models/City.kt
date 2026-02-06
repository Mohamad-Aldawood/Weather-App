package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.Serializable


@Serializable

data class City(

    var id: Int? = null,
    var name: String? = null,
    var coord: Coord? = Coord(),
    var country: String? = null,
    var population: Int? = null,
    var timezone: Int? = null,
    var sunrise: Int? = null,
    var sunset: Int? = null

)