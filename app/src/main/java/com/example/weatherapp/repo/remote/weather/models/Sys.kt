package com.example.weatherapp.repo.remote.weather.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Sys(

    @SerialName("pod") var pod: String? = null,
    @SerialName("type") var type: Int? = null,
    @SerialName("id") var id: Int? = null,
    @SerialName("country") var country: String? = null,
    @SerialName("sunrise") var sunrise: Int? = null,
    @SerialName("sunset") var sunset: Int? = null

)