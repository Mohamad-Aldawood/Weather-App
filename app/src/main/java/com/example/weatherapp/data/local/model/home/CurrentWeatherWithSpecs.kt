package com.example.weatherapp.data.local.model.home

import androidx.room.Embedded
import androidx.room.Relation

data class CurrentWeatherWithSpecs(
    @Embedded val weather: CurrentWeatherInfoEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "weatherId"
    )
    val specs: List<TodayTempSpecificationEntity>
)
