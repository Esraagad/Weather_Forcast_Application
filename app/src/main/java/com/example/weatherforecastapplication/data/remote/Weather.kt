package com.example.weatherforecastapplication.data.remote

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)