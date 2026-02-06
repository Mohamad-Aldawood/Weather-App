package com.example.weatherapp.core

import androidx.compose.ui.graphics.Color
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.AnchorGray
import com.example.weatherapp.ui.theme.DuskyTeal
import com.example.weatherapp.ui.theme.NatureGreen

enum class WeatherEnum(val title: String, val background: Int, val color: Color) {
    Sunny(
        title = "Sunny",
        background = R.drawable.forest_sunny,
        color = NatureGreen
    ),
    Cloudy(title = "Cloudy", background = R.drawable.forest_cloudy, color = DuskyTeal),
    Rain(title = "Rain", background = R.drawable.forest_rainy, color = AnchorGray)
}