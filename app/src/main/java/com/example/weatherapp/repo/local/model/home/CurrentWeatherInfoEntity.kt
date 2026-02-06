package com.example.weatherapp.repo.local.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val weatherTitle: String?,
    val backgroundImage: Int?,
    val backgroundColor: Long? // Color stored as ARGB
)