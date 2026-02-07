package com.example.weatherapp.data.local.model.home

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.weatherapp.screen.home.model.ColumnAlignment

@Entity(
    tableName = "today_temp_spec",
    foreignKeys = [
        ForeignKey(
            entity = CurrentWeatherInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["weatherId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("weatherId")]
)
data class TodayTempSpecificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val weatherId: Int,
    val value: String,
    val weight: Float,
    val title: String,
    val columnAlignment: ColumnAlignment
)
