package com.example.weatherapp.repo.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.repo.local.model.favourite.FavouriteLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLocationDao {

    @Query("SELECT * FROM favourite_locations")
    fun getAllFavourites(): Flow<List<FavouriteLocation>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertFavourite(location: FavouriteLocation)

    @Delete
    suspend fun deleteFavourite(location: FavouriteLocation)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_locations WHERE lat = :lat AND lon = :lon)")
    suspend fun isFavourite(lat: Double, lon: Double): Boolean
}