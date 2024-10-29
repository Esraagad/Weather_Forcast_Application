package com.example.weatherforecastapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather_forecast")
data class WeatherForecastEntity(
    @PrimaryKey
    val id: String,
    val cityName: String,
    val longitude: Double,
    val latitude: Double,
    val icon: String,
    val temperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val feelsLikeTemperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val date: Int
):Serializable