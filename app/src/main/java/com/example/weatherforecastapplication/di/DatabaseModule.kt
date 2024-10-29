package com.example.weatherforecastapplication.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecastapplication.data.local.WeatherForecastDao
import com.example.weatherforecastapplication.data.local.WeatherForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherForecastDatabase {
        return Room.databaseBuilder(context, WeatherForecastDatabase::class.java, "Weather_DB.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherForecastDao(db: WeatherForecastDatabase): WeatherForecastDao {
        return db.weatherForecastDao()
    }

}