package com.example.weatherapp.util

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.weatherapp.repo.local.model.home.CurrentWeatherInfoEntity
import com.example.weatherapp.repo.local.model.home.CurrentWeatherWithSpecs
import com.example.weatherapp.repo.local.model.home.TodayTempSpecificationEntity
import com.example.weatherapp.repo.local.model.home.WeatherItemEntity
import com.example.weatherapp.screen.home.model.ColumnAlignment
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.TodayTempSpecification
import com.example.weatherapp.screen.home.model.WeatherItem
import kotlin.math.roundToInt

fun convertToCelsius(tempKelvin: Double?): String =
    (tempKelvin?.minus(273.15))?.roundToInt().toString()

fun ColumnAlignment.toAlignment(): Alignment.Horizontal = when (this) {
    ColumnAlignment.Start -> Alignment.Start
    ColumnAlignment.Center -> Alignment.CenterHorizontally
    ColumnAlignment.End -> Alignment.End
}

fun CurrentWeatherWithSpecs.toUiModel(): CurrentWeatherInfo =
    CurrentWeatherInfo(
        weatherTitle = weather.weatherTitle,
        backgroundImage = weather.backgroundImage,
        backgroundColor = weather.backgroundColor?.let { Color(it) },
        todayTempStatus = specs.map {
            TodayTempSpecification(
                value = it.value,
                weight = it.weight,
                title = it.title,
                columnAlignment = it.columnAlignment
            )
        }
    )

fun WeatherItemEntity.toUi(): WeatherItem =
    WeatherItem(dayText, temp, icon)

fun CurrentWeatherInfo.toEntity(): CurrentWeatherInfoEntity =
    CurrentWeatherInfoEntity(
        weatherTitle = weatherTitle,
        backgroundImage = backgroundImage,
        backgroundColor = backgroundColor?.toArgb()?.toLong()
    )

fun TodayTempSpecification.toEntity(weatherId: Int) =
    TodayTempSpecificationEntity(
        weatherId = weatherId,
        value = value,
        weight = weight,
        title = title,
        columnAlignment = columnAlignment
    )

fun WeatherItem.toEntity() =
    WeatherItemEntity(dayText = dayText, temp = temp, icon = icon)

