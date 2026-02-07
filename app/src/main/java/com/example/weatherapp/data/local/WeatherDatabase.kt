package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.local.dao.FavouriteLocationDao
import com.example.weatherapp.data.local.dao.WeatherDao
import com.example.weatherapp.data.local.model.favourite.FavouriteLocation
import com.example.weatherapp.data.local.model.home.CurrentWeatherInfoEntity
import com.example.weatherapp.data.local.model.home.TodayTempSpecificationEntity
import com.example.weatherapp.data.local.model.home.WeatherItemEntity

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
