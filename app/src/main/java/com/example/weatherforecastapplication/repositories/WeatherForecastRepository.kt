package com.example.weatherforecastapplication.repositories


import com.example.weatherforecastapplication.data.local.WeatherForecastDao
import com.example.weatherforecastapplication.data.local.WeatherForecastEntity
import com.example.weatherforecastapplication.data.remote.WeatherForecast
import com.example.weatherforecastapplication.data.remote.WeatherForecastApi
import com.example.weatherforecastapplication.other.ErrorMessages
import com.example.weatherforecastapplication.other.Resource
import javax.inject.Inject


class WeatherForecastRepository @Inject constructor(
    private val weatherApiService: WeatherForecastApi,
    private val weatherDao: WeatherForecastDao
) {

    suspend fun fetchWeatherForecast(
        longitude: Double,
        latitude: Double
    ): Resource<List<WeatherForecastEntity>> {
        return try {
            val response = weatherApiService.getWeatherForecast(lon = longitude, lat = latitude)
            if (response.isSuccessful) {
                response.body()?.let {
                    val forecastEntity = fromWeatherForecast(it)
                    weatherDao.insertAll(forecastEntity)
                    Resource.Success(forecastEntity)
                } ?: Resource.Error(ErrorMessages.NO_DATA_FOUND)
            } else {
                fetchFromCache(longitude, latitude, "Error: ${response.message()}")
            }
        } catch (e: Exception) {
            fetchFromCache(longitude, latitude, ErrorMessages.NO_INTERNET_CONNECTION)
        }
    }

     suspend fun fetchFromCache(
        longitude: Double,
        latitude: Double,
        errorMessage: String
    ): Resource<List<WeatherForecastEntity>> {
        try {
            val cachedData = weatherDao.getForecastByCoordinates(longitude, latitude, System.currentTimeMillis().toInt())
            return if (cachedData.isNullOrEmpty())
                Resource.Error(errorMessage)
            else
                Resource.Success(cachedData, ErrorMessages.DATA_FROM_CACHE)
        } catch (e: Exception) {
            return Resource.Error(errorMessage)
        }
        return Resource.Error(errorMessage)
    }

     fun fromWeatherForecast(weatherForecast: WeatherForecast): List<WeatherForecastEntity> {
        val forecastList = mutableListOf<WeatherForecastEntity>()
        weatherForecast.list.forEach {
            val forecastEntity = WeatherForecastEntity(
                id = (weatherForecast.city.id.toString()).plus(it.dtTxt),
                cityName = weatherForecast.city.name,
                longitude = weatherForecast.city.coordinators.lon,
                latitude = weatherForecast.city.coordinators.lat,
                icon = it.weather[0].icon,
                temperature = it.main.temp,
                maxTemperature = it.main.tempMax,
                minTemperature = it.main.tempMin,
                feelsLikeTemperature = it.main.feelsLike,
                description = it.weather[0].description,
                humidity = it.main.humidity,
                windSpeed = it.wind.speed,
                pressure = it.main.pressure,
                date = it.dt
            )
            forecastList.add(forecastEntity)
        }
        return forecastList
    }
}