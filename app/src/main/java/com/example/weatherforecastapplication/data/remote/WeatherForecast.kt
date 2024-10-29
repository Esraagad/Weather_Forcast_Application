package com.example.weatherforecastapplication.data.remote

data class WeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<CityWeather>,
    val city: CityInfo
)