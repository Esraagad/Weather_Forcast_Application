package com.example.weatherforecastapplication.di

//import com.example.weatherforecastapplication.data.local.WeatherForecastDao
import com.example.weatherforecastapplication.data.local.WeatherForecastDao
import com.example.weatherforecastapplication.data.local.WeatherForecastDatabase
import com.example.weatherforecastapplication.data.remote.WeatherForecastApi
import com.example.weatherforecastapplication.repositories.WeatherForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(dao: WeatherForecastDao,
        api: WeatherForecastApi
    ): WeatherForecastRepository {
        return WeatherForecastRepository(api,dao)
    }
}