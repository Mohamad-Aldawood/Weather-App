package com.example.weatherapp.screen.home.model

import androidx.compose.ui.graphics.Color

data class CurrentWeatherInfo(
    val todayTempStatus: List<TodayTempSpecification>? = null,
    val weatherTitle: String? = null,
    val backgroundImage: Int? = null,
    val backgroundColor: Color? = null,
)
