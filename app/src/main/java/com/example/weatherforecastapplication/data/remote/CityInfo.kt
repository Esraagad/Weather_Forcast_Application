package com.example.weatherforecastapplication.data.remote

import com.google.gson.annotations.SerializedName

data class CityInfo(
    @SerializedName("coord")
    val coordinators: Coordinators,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)