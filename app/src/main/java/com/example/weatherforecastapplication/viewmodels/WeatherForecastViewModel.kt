package com.example.weatherforecastapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.data.local.WeatherForecastEntity
import com.example.weatherforecastapplication.other.Resource
import com.example.weatherforecastapplication.repositories.WeatherForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    val repository: WeatherForecastRepository
) : ViewModel() {

    private val _weatherForecast = MutableLiveData<Resource<List<WeatherForecastEntity>>>()
    val weatherForecast: LiveData<Resource<List<WeatherForecastEntity>>> = _weatherForecast

    fun fetchWeather(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            _weatherForecast.value = Resource.Loading()
            val result = repository.fetchWeatherForecast(longitude, latitude)
            _weatherForecast.value = result
        }
    }
}