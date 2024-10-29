package com.example.weatherforecastapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherForecastDao {

    @Query("SELECT * FROM weather_forecast WHERE latitude = :latitude AND longitude = :longitude " +
            "AND date >= :currentTime ORDER BY date ASC")
    suspend fun getForecastByCoordinates(
        longitude: Double,
        latitude: Double,
        currentTime: Int
    ): List<WeatherForecastEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecasts: List<WeatherForecastEntity>)
}