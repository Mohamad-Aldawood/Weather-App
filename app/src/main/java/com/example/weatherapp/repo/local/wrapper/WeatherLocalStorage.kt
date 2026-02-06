package com.example.weatherapp.repo.local.wrapper

import com.example.weatherapp.repo.local.dao.WeatherDao
import com.example.weatherapp.repo.local.model.home.WeatherItemEntity
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.WeatherItem
import com.example.weatherapp.util.toEntity
import com.example.weatherapp.util.toUi
import com.example.weatherapp.util.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherLocalStorage @Inject constructor(
    private val dao: WeatherDao
) {

    fun getCurrentWeather(): Flow<CurrentWeatherInfo?> =
        dao.getLatestWeather().map { it?.toUiModel() }

    suspend fun saveCurrentWeather(info: CurrentWeatherInfo) {
        dao.insertWeatherWithSpecs(
            info = info.toEntity(),
            specs = info.todayTempStatus?.map { it.toEntity(0) } ?: emptyList()
        )
    }

    fun getForecast(): Flow<List<WeatherItem>> =
        dao.getWeatherItems().map { it.map(WeatherItemEntity::toUi) }

    suspend fun saveForecast(items: List<WeatherItem>) {
        dao.insertWeatherItems(items.map { it.toEntity() })
    }
}
