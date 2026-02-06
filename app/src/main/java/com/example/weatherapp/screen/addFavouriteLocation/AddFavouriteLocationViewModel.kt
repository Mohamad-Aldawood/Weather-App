package com.example.weatherapp.screen.addFavouriteLocation

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.BaseViewModel
import com.example.weatherapp.repo.local.model.favourite.FavouriteLocation
import com.example.weatherapp.repo.local.wrapper.FavouriteLocalStorage
import com.example.weatherapp.repo.remote.Result
import com.example.weatherapp.repo.remote.geo.GeoApiService
import com.example.weatherapp.repo.remote.geo.models.GeoCodingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AddFavouriteLocationEvents() {
    data class DisplayError(val message: String?) : AddFavouriteLocationEvents()
}

sealed class AddFavouriteLocationActions() {
    data object SaveAndSearch : AddFavouriteLocationActions()

    data class UpdateQuery(val query: String) : AddFavouriteLocationActions()
}

data class AddFavouriteLocationState(val query: String = "")

@HiltViewModel
class AddFavouriteLocationViewModel @Inject constructor(
    private val geoApiService: GeoApiService,
    private val favouriteLocalStorage: FavouriteLocalStorage
) : BaseViewModel<AddFavouriteLocationState, AddFavouriteLocationEvents, AddFavouriteLocationActions>(
    initialState = AddFavouriteLocationState()
) {

    /**
     * ############
     *
     *  handle store the new location to the database
     *
     * ############
     * */
    private fun addFavouriteToLocalStorage(favouriteLocation: FavouriteLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteLocalStorage.addFavourite(favouriteLocation)
            sendEvent(AddFavouriteLocationEvents.DisplayError("Saved Successfully"))
        }
    }

    override fun handleAction(action: AddFavouriteLocationActions) {
        when (action) {
            is AddFavouriteLocationActions.UpdateQuery -> handleUpdateQuery(action.query)
            is AddFavouriteLocationActions.SaveAndSearch -> handleFetchLocation()
        }
    }

    /**
     * ###########
     *
     * handle the query from the user to be requested form the api
     *
     * ###########
     * */
    fun handleFetchLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val query = mutableState.value.query
            geoApiService.getGeocodingForQuery(query)
                .catch { sendEvent(AddFavouriteLocationEvents.DisplayError(it.message)) }
                .collect { result ->
                    handleGeocodingResult(result)
                }
        }
    }

    /**
     * ########
     *
     * handle the delivered response to be stored to DB
     *
     * ########
     * */
    private fun handleGeocodingResult(result: Result<GeoCodingResponse>) {
        when (result) {
            is Result.Success -> addFavouriteToLocalStorage(
                FavouriteLocation(
                    cityName = result.data.name,
                    lat = result.data.lat,
                    lon = result.data.lon,
                    country = result.data.country,
                )
            )

            is Result.Error -> {
                sendEvent(AddFavouriteLocationEvents.DisplayError(result.exception.message))
            }

            Result.Loading -> Unit
        }
    }

    /**
     * #########
     *
     * handle the each character send from the ui
     *
     * #########
     * */

    private fun handleUpdateQuery(value: String) {
        mutableState.update {
            it.copy(query = value)
        }
    }
}