package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.repo.remote.geo.GeoApiService
import com.example.weatherapp.repo.remote.geo.GeoApiServiceImpl
import com.example.weatherapp.repo.remote.weather.ApiService
import com.example.weatherapp.repo.remote.weather.ApiServiceImpl
import com.example.weatherapp.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true

                })
            }

        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService = ApiServiceImpl(httpClient)

    @Singleton
    @Provides
    fun provideGeoApiService(httpClient: HttpClient): GeoApiService = GeoApiServiceImpl(httpClient)

    @Singleton
    @Provides
    fun provideNetworkUtil(
        @ApplicationContext context: Context
    ): NetworkUtils = NetworkUtils(context)

}
