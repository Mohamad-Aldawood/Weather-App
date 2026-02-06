package com.example.weatherapp.repo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.weatherapp.repo.local.model.home.CurrentWeatherInfoEntity
import com.example.weatherapp.repo.local.model.home.CurrentWeatherWithSpecs
import com.example.weatherapp.repo.local.model.home.TodayTempSpecificationEntity
import com.example.weatherapp.repo.local.model.home.WeatherItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    /* ---------- Current Weather ---------- */

    @Transaction
    @Query("SELECT * FROM current_weather ORDER BY id DESC LIMIT 1")
    fun getLatestWeather(): Flow<CurrentWeatherWithSpecs?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(info: CurrentWeatherInfoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecs(specs: List<TodayTempSpecificationEntity>)

    @Transaction
    suspend fun insertWeatherWithSpecs(
        info: CurrentWeatherInfoEntity,
        specs: List<TodayTempSpecificationEntity>
    ) {
        val weatherId = insertWeather(info).toInt()
        insertSpecs(specs.map { it.copy(weatherId = weatherId) })
    }

    @Query("DELETE FROM current_weather")
    suspend fun clearWeather()

    /* ---------- Forecast Items ---------- */

    @Query("SELECT * FROM weather_item ORDER BY id DESC LIMIT 6")
    fun getWeatherItems(): Flow<List<WeatherItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherItems(items: List<WeatherItemEntity>)

    @Query("DELETE FROM weather_item")
    suspend fun clearWeatherItems()
}
