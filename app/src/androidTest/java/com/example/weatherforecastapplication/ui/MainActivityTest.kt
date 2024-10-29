package com.example.weatherforecastapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherforecastapplication.other.Resource
import com.example.weatherforecastapplication.viewmodels.WeatherForecastViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.local.WeatherForecastEntity
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.`when`

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testWeatherElementsDisplayed() {
        val weatherViewModel = mockk<WeatherForecastViewModel>(relaxed = true)
        val mockLiveData = MutableLiveData<Resource<List<WeatherForecastEntity>>>()
        `when`(weatherViewModel.weatherForecast).thenReturn(mockLiveData)

        val mockWeatherForecastEntityList = listOf(
            WeatherForecastEntity(
                id = "1",
                cityName = "New York",
                longitude = -74.0060,
                latitude = 40.7128,
                icon = "01d",
                temperature = 25.0,
                maxTemperature = 28.0,
                minTemperature = 20.0,
                feelsLikeTemperature = 26.5,
                description = "clear sky",
                humidity = 60,
                windSpeed = 5.5,
                pressure = 1015,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "2",
                cityName = "Los Angeles",
                longitude = -118.2437,
                latitude = 34.0522,
                icon = "02d",
                temperature = 22.0,
                maxTemperature = 24.0,
                minTemperature = 18.0,
                feelsLikeTemperature = 21.0,
                description = "few clouds",
                humidity = 50,
                windSpeed = 3.0,
                pressure = 1012,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "3",
                cityName = "Chicago",
                longitude = -87.6298,
                latitude = 41.8781,
                icon = "10d",
                temperature = 18.0,
                maxTemperature = 20.0,
                minTemperature = 15.0,
                feelsLikeTemperature = 17.0,
                description = "light rain",
                humidity = 70,
                windSpeed = 6.5,
                pressure = 1008,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "4",
                cityName = "London",
                longitude = -0.1276,
                latitude = 51.5074,
                icon = "03d",
                temperature = 16.0,
                maxTemperature = 18.0,
                minTemperature = 12.0,
                feelsLikeTemperature = 15.0,
                description = "scattered clouds",
                humidity = 80,
                windSpeed = 4.5,
                pressure = 1020,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "5",
                cityName = "Tokyo",
                longitude = 139.6917,
                latitude = 35.6895,
                icon = "04d",
                temperature = 28.0,
                maxTemperature = 30.0,
                minTemperature = 25.0,
                feelsLikeTemperature = 29.0,
                description = "broken clouds",
                humidity = 65,
                windSpeed = 5.0,
                pressure = 1010,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "6",
                cityName = "Sydney",
                longitude = 151.2093,
                latitude = -33.8688,
                icon = "50d",
                temperature = 20.0,
                maxTemperature = 22.0,
                minTemperature = 18.0,
                feelsLikeTemperature = 19.0,
                description = "mist",
                humidity = 90,
                windSpeed = 2.5,
                pressure = 1005,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "7",
                cityName = "Paris",
                longitude = 2.3522,
                latitude = 48.8566,
                icon = "09d",
                temperature = 19.0,
                maxTemperature = 21.0,
                minTemperature = 17.0,
                feelsLikeTemperature = 18.5,
                description = "shower rain",
                humidity = 75,
                windSpeed = 4.0,
                pressure = 1018,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "8",
                cityName = "Berlin",
                longitude = 13.4050,
                latitude = 52.5200,
                icon = "11d",
                temperature = 15.0,
                maxTemperature = 17.0,
                minTemperature = 12.0,
                feelsLikeTemperature = 14.0,
                description = "thunderstorm",
                humidity = 85,
                windSpeed = 7.0,
                pressure = 1009,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "9",
                cityName = "Dubai",
                longitude = 55.2708,
                latitude = 25.2048,
                icon = "01d",
                temperature = 35.0,
                maxTemperature = 38.0,
                minTemperature = 32.0,
                feelsLikeTemperature = 36.0,
                description = "clear sky",
                humidity = 30,
                windSpeed = 3.0,
                pressure = 1003,
                date = 20241025
            ),
            WeatherForecastEntity(
                id = "10",
                cityName = "Cape Town",
                longitude = 18.4241,
                latitude = -33.9249,
                icon = "13d",
                temperature = 12.0,
                maxTemperature = 15.0,
                minTemperature = 10.0,
                feelsLikeTemperature = 11.0,
                description = "snow",
                humidity = 85,
                windSpeed = 5.5,
                pressure = 1013,
                date = 20241025
            )
        )

        // Set LiveData value to test the UI response
        mockLiveData.postValue(Resource.Success((mockWeatherForecastEntityList)))

        // Verify the RecyclerView is updated
        onView(withId(R.id.rv_city_weather_forecast)).check(matches(hasMinimumChildCount(1)))
    }
}