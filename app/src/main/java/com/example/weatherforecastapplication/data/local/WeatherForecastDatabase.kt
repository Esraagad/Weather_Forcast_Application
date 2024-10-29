package com.example.weatherforecastapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherForecastEntity::class], version = 1)
abstract class WeatherForecastDatabase : RoomDatabase() {

    abstract fun weatherForecastDao(): WeatherForecastDao
}