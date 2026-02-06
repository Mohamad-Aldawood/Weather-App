package com.example.weatherapp.screen.myfavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repo.local.model.favourite.FavouriteLocation
import com.example.weatherapp.repo.local.wrapper.FavouriteLocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyFavouriteViewModel @Inject constructor(
    favouriteLocalStorage: FavouriteLocalStorage,
) : ViewModel() {
    /**
     * ######
     *
     * one variable will be used since the screen is just for display the favourite places
     *
     * #####
     * */
    val favourites: StateFlow<List<FavouriteLocation>> =
        favouriteLocalStorage.getFavourites()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )
}