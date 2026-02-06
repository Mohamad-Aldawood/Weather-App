package com.example.weatherapp.repo.remote.geo

import com.example.weatherapp.repo.remote.Result
import com.example.weatherapp.repo.remote.geo.models.GeoCodingResponse
import kotlinx.coroutines.flow.Flow

interface GeoApiService {
    fun getGeocodingForQuery(query: String): Flow<Result<GeoCodingResponse>>
}