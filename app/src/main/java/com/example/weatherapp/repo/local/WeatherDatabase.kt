package com.example.weatherapp.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.repo.local.dao.FavouriteLocationDao
import com.example.weatherapp.repo.local.dao.WeatherDao
import com.example.weatherapp.repo.local.model.favourite.FavouriteLocation
import com.example.weatherapp.repo.local.model.home.CurrentWeatherInfoEntity
import com.example.weatherapp.repo.local.model.home.TodayTempSpecificationEntity
import com.example.weatherapp.repo.local.model.home.WeatherItemEntity

@Database(
    entities = [FavouriteLocation::class,
        CurrentWeatherInfoEntity::class,
        TodayTempSpecificationEntity::class,
        WeatherItemEntity::class
    ], version = 1, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun favouriteLocationDao(): FavouriteLocationDao
    abstract fun weatherDao(): WeatherDao

}
