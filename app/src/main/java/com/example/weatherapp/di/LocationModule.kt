package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.util.AppLocationManager
import com.example.weatherapp.util.GetLocationManager
import com.example.weatherapp.util.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): AppLocationManager {
        return AppLocationManager(context)
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(
        appLocationManager: AppLocationManager
    ): LocationProvider {
        return GetLocationManager(appLocationManager)
    }
}
