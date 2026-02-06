package com.example.weatherapp.repo.remote.geo

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.repo.remote.Result
import com.example.weatherapp.repo.remote.geo.models.GeoCodingResponse
import com.example.weatherapp.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeoApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : GeoApiService {
    override fun getGeocodingForQuery(query: String): Flow<Result<GeoCodingResponse>> = flow {
        try {
            val responseBody: List<GeoCodingResponse> =
                httpClient.get(BuildConfig.BASE_URL + Constants.GEO) {
                    url {
                        protocol = URLProtocol.Companion.HTTPS
                        parameters.append(Constants.Q, query)
                        parameters.append(Constants.LIMIT, "1")
                        parameters.append(Constants.APP_ID, BuildConfig.API_KEY)
                    }
                }.body()
            if (responseBody.isNotEmpty()) {
                emit(Result.Success(responseBody.first()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e))
        }
    }
}