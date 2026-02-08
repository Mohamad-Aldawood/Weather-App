package com.example.weatherapp.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.BaseViewModel
import com.example.weatherapp.data.local.localStorage.WeatherLocalStorage
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.weather.ApiService
import com.example.weatherapp.data.remote.weather.models.CurrentWeather
import com.example.weatherapp.data.remote.weather.models.FiveDaysWeather
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.WeatherItem
import com.example.weatherapp.util.LocationProvider
import com.example.weatherapp.util.NetworkState
import com.example.weatherapp.util.NetworkUtils
import com.example.weatherapp.util.toCurrentWeatherInfo
import com.example.weatherapp.util.toDailyWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val showLoader: Boolean = true,
    val currentWeatherInfo: CurrentWeatherInfo = CurrentWeatherInfo(
        todayTempStatus = emptyList(),
        weatherTitle = ""
    ),
    val list: List<WeatherItem> = mutableListOf<WeatherItem>(),
)

sealed class HomeEvents() {
    data class DisplayErrorMsg(val error: String) : HomeEvents()
}

sealed class HomeAction() {
    data object GetWeatherInfo : HomeAction()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val locationProvider: LocationProvider,
    private val weatherLocalStorage: WeatherLocalStorage,
    private val networkUtils: NetworkUtils
) : BaseViewModel<HomeState, HomeEvents, HomeAction>(
    initialState = HomeState()
) {
    override fun handleAction(action: HomeAction) {
        when (action) {
            is HomeAction.GetWeatherInfo -> handleGetWeatherInfo()
        }
    }

    private val TAG = "HomeViewModel"

    /**
     * #######
     *
     * observing network changing
     *
     * #######
     * */
    val networkState = networkUtils.networkState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NetworkState.Unavailable
        )

    /**
     * #######
     *
     * handle data gathering eather by api or by DB
     *
     * #######
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleGetWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            networkState.collect {
                when (it) {
                    NetworkState.Available -> {
                        handleGetInfoUsingApi()
                    }

                    NetworkState.Unavailable -> {
                        handleGetDataUsingLocalStorage()
                    }
                }
            }

        }

    }


    /**
     * #######
     *
     * handle getting data from api
     *
     * ######
     * */
    private fun handleGetInfoUsingApi() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation() ?: return@launch

            if (location == null) {
                mutableState.update { it.copy(showLoader = false) }
                return@launch
            }
            combine(
                apiService.getCurrentWeatherInfo(
                    location.first.toString(),
                    location.second.toString()
                ),
                apiService.getFiveDaysWeatherInfo(
                    location.first.toString(),
                    location.second.toString()
                )
            ) { currentWeather, fiveDays ->
                currentWeather to fiveDays
            }.collect { (currentWeather, fiveDays) ->
                when {
                    currentWeather is Result.Success && fiveDays is Result.Success ->
                        handleSuccess(currentWeather, fiveDays)

                    currentWeather is Result.Error || fiveDays is Result.Error ->
                        handleError(currentWeather, fiveDays)
                }

            }
        }

    }

    /**
     * #######
     *
     * this method to handle the received data from api in success state
     *
     * ######
     * */
    fun handleSuccess(
        currentWeather: Result.Success<CurrentWeather>,
        fiveDays: Result.Success<FiveDaysWeather>,
    ) {
        val current = currentWeather.data.toCurrentWeatherInfo()
        val daily = fiveDays.data.toDailyWeather()

        mutableState.update {
            it.copy(
                showLoader = false,
                currentWeatherInfo = current,
                list = daily
            )
        }

        handleStoringResultInDB(current, daily)
    }

    /**
     * #######
     *
     * this method to handle error state when retrieving state from api
     *
     * #######
     * */
    fun handleError(
        currentWeather: Result<CurrentWeather>,
        fiveDays: Result<FiveDaysWeather>
    ) {
        when {
            currentWeather is Result.Error -> {
                sendEvent(HomeEvents.DisplayErrorMsg(currentWeather.exception.message.toString()))
            }

            fiveDays is Result.Error -> {
                sendEvent(HomeEvents.DisplayErrorMsg(fiveDays.exception.message.toString()))
            }
        }
    }

    /**
     * ######
     *
     * handling storing result in DB after fetching from api
     *
     * ######
     * */

    private fun handleStoringResultInDB(
        result: CurrentWeatherInfo,
        listWeatherItem: List<WeatherItem>
    ) {
        viewModelScope.launch {
            weatherLocalStorage.saveCurrentWeather(result)
            weatherLocalStorage.saveForecast(listWeatherItem.reversed())
        }
    }

    /**
     * ######
     *
     * handle getting data from  local storage
     *
     * ######
     * */

    private fun handleGetDataUsingLocalStorage() {
        viewModelScope.launch {
            combine(
                weatherLocalStorage.getCurrentWeather(),
                weatherLocalStorage.getForecast()
            ) { currentWeather, forecast ->
                currentWeather to forecast
            }.collect { (currentWeather, forecast) ->
                if (currentWeather != null && forecast != null) {
                    mutableState.update {
                        it.copy(
                            currentWeatherInfo = currentWeather,
                            list = forecast,
                            showLoader = false
                        )
                    }
                }
            }
        }

    }

}
