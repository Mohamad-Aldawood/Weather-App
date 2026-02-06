package com.example.weatherapp.repo.remote.weather

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.repo.remote.Result
import com.example.weatherapp.repo.remote.weather.models.CurrentWeather
import com.example.weatherapp.repo.remote.weather.models.FiveDaysWeather
import com.example.weatherapp.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//
class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {

    override fun getFiveDaysWeatherInfo(
        lat: String,
        lon: String
    ): Flow<FiveDaysWeather> = flow {
        try {
            val responseBody: FiveDaysWeather =
                httpClient.get(BuildConfig.BASE_URL + Constants.DATA_VERSION + Constants.FORECAST) {
                    url {
                        protocol = URLProtocol.Companion.HTTPS
                        parameters.append(Constants.LAT, lat)
                        parameters.append(Constants.LON, lon)
                        parameters.append(Constants.APP_ID, BuildConfig.API_KEY)
                        parameters.append("units", "Kelvin")
                    }
                }.body()
            emit(responseBody)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getCurrentWeatherInfo(
        lat: String,
        lon: String
    ): Flow<Result<CurrentWeather>> = flow {
        try {
            val responseBody: CurrentWeather =
                httpClient.get(BuildConfig.BASE_URL + Constants.DATA_VERSION + Constants.WEATHER) {
                    url {
                        protocol = URLProtocol.Companion.HTTPS
                        parameters.append(Constants.LAT, lat)
                        parameters.append(Constants.LON, lon)
                        parameters.append(Constants.APP_ID, BuildConfig.API_KEY)
                    }
                }.body()
            emit(Result.Success(responseBody))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e))
        }
    }
}

