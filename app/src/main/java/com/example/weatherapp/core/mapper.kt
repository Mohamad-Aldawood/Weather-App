package com.example.weatherapp.core

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.repo.remote.weather.models.CurrentWeather
import com.example.weatherapp.repo.remote.weather.models.FiveDaysWeather
import com.example.weatherapp.screen.home.model.ColumnAlignment
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.TodayTempSpecification
import com.example.weatherapp.screen.home.model.WeatherItem
import com.example.weatherapp.util.ImageAssestHelper
import com.example.weatherapp.util.convertToCelsius
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
fun FiveDaysWeather.toDailyWeather(): List<WeatherItem> {

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val nowTime = LocalTime.now()
    val nowMinutes = nowTime.hour * 60 + nowTime.minute

    return this.weatherInfo
        .filter { !it.dtTxt.isNullOrBlank() }
        .groupBy { item ->
            // Parse full date-time -> LocalDate
            val dateTime = LocalDateTime.parse(item.dtTxt, inputFormatter)
            dateTime.toLocalDate()
        }
        .toSortedMap()
        .map { (localDate, itemsForDay) ->

            val closestItem = itemsForDay.minBy { item ->
                val time = LocalDateTime.parse(item.dtTxt, inputFormatter).toLocalTime()
                val minutes = time.hour * 60 + time.minute
                abs(minutes - nowMinutes)
            }

            val tempKelvin = closestItem.main?.temp ?: 0.0
            val tempText = convertToCelsius(tempKelvin)

            val dayName = localDate.dayOfWeek
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)

            val weatherId = closestItem.weather.firstOrNull()?.id ?: 800

            WeatherItem(
                dayText = dayName,
                temp = tempText,
                icon = ImageAssestHelper.getIcon(weatherId)
            )
        }
}

fun CurrentWeather.toCurrentWeatherInfo(): CurrentWeatherInfo {
    val weatherObj = ImageAssestHelper.getWeatherEnum(this.weather[0].id ?: 0)

    val todayTempStatus: List<TodayTempSpecification> = listOf(
        TodayTempSpecification(
            title = "min",
            value = convertToCelsius(this.main?.tempMin),
            weight = 2f,
            columnAlignment = ColumnAlignment.Start
        ),
        TodayTempSpecification(
            title = "current",
            value = convertToCelsius(this.main?.temp),
            weight = 1f,
            columnAlignment = ColumnAlignment.Center
        ),
        TodayTempSpecification(
            title = "max",
            value = convertToCelsius(this.main?.tempMax),
            weight = 2f,
            columnAlignment = ColumnAlignment.End
        ),
    )

    return CurrentWeatherInfo(
        todayTempStatus = todayTempStatus,
        weatherTitle = this.weather[0].main.toString(),
        backgroundImage = weatherObj?.background,
        backgroundColor = weatherObj?.color
    )
}

