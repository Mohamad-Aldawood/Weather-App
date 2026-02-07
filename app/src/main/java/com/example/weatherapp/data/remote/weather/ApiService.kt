package com.example.weatherapp.data.remote.weather

import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.weather.models.CurrentWeather
import com.example.weatherapp.data.remote.weather.models.FiveDaysWeather
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getFiveDaysWeatherInfo(lat: String, lon: String): Flow<FiveDaysWeather>
    fun getCurrentWeatherInfo(lat: String, lon: String): Flow<Result<CurrentWeather>>

}