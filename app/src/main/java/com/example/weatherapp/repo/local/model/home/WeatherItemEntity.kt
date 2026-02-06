package com.example.weatherapp.repo.local.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_item")
data class WeatherItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val dayText: String,
    val temp: String,
    val icon: Int
)
