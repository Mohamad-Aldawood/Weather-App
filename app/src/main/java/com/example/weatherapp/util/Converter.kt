package com.example.weatherapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.weatherapp.data.local.model.home.CurrentWeatherInfoEntity
import com.example.weatherapp.data.local.model.home.CurrentWeatherWithSpecs
import com.example.weatherapp.data.local.model.home.TodayTempSpecificationEntity
import com.example.weatherapp.data.local.model.home.WeatherItemEntity
import com.example.weatherapp.data.remote.weather.models.CurrentWeather
import com.example.weatherapp.data.remote.weather.models.FiveDaysWeather
import com.example.weatherapp.screen.home.model.ColumnAlignment
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.TodayTempSpecification
import com.example.weatherapp.screen.home.model.WeatherItem
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.abs
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