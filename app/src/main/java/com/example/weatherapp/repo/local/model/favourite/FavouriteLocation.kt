package com.example.weatherapp.repo.local.model.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_locations")
data class FavouriteLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cityName: String,
    val lat: Double,
    val lon: Double,

    val country: String? = null,
    val timezone: String? = null
)