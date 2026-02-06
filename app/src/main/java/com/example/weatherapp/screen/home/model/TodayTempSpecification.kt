package com.example.weatherapp.screen.home.model

data class TodayTempSpecification(
    val value: String, val weight: Float, val title: String,
    val columnAlignment: ColumnAlignment,
)

enum class ColumnAlignment {
    Start, Center, End;

}
