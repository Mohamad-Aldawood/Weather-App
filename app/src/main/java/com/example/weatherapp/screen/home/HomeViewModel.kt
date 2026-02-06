package com.example.weatherapp.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.BaseViewModel
import com.example.weatherapp.core.toCurrentWeatherInfo
import com.example.weatherapp.core.toDailyWeather
import com.example.weatherapp.repo.local.wrapper.WeatherLocalStorage
import com.example.weatherapp.repo.remote.Result
import com.example.weatherapp.repo.remote.weather.ApiService
import com.example.weatherapp.repo.remote.weather.models.CurrentWeather
import com.example.weatherapp.screen.home.model.CurrentWeatherInfo
import com.example.weatherapp.screen.home.model.WeatherItem
import com.example.weatherapp.util.LocationProvider
import com.example.weatherapp.util.NetworkState
import com.example.weatherapp.util.NetworkUtils
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
            ) { currentWeather, fivedays ->
                currentWeather to fivedays
            }.collect { (currentWeather, fivedays) ->
                if (currentWeather != null && fivedays != null) {
                    val result =
                        (currentWeather as Result.Success<CurrentWeather>).data.toCurrentWeatherInfo()
                    val listWeatherItem = fivedays.toDailyWeather()
                    mutableState.update {
                        it.copy(
                            showLoader = false,
                            currentWeatherInfo = result,
                            list = listWeatherItem
                        )
                    }
                    handleStoringResultInDB(result, listWeatherItem)
                }

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
