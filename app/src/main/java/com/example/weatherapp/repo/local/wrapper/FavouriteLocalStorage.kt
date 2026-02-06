package com.example.weatherapp.repo.local.wrapper

import com.example.weatherapp.repo.local.dao.FavouriteLocationDao
import com.example.weatherapp.repo.local.model.favourite.FavouriteLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteLocalStorage @Inject constructor(
    private val favouriteDao: FavouriteLocationDao
) {
    fun getFavourites(): Flow<List<FavouriteLocation>> =
        favouriteDao.getAllFavourites()

    suspend fun addFavourite(location: FavouriteLocation) {
        favouriteDao.insertFavourite(location)
    }

    suspend fun removeFavourite(location: FavouriteLocation) {
        favouriteDao.deleteFavourite(location)
    }

    suspend fun isFavourite(lat: Double, lon: Double): Boolean {
        return favouriteDao.isFavourite(lat, lon)
    }
}