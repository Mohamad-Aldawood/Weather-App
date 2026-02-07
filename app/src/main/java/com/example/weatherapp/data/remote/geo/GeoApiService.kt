package com.example.weatherapp.data.remote.geo

import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.geo.models.GeoCodingResponse
import kotlinx.coroutines.flow.Flow

interface GeoApiService {
    fun getGeocodingForQuery(query: String): Flow<Result<GeoCodingResponse>>
}