package com.example.weatherforecastapplication.data.remote

import com.example.weatherforecastapplication.other.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): retrofit2.Response<WeatherForecast>
}