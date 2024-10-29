package com.example.weatherforecastapplication.repositories

import com.example.weatherforecastapplication.data.local.WeatherForecastDao
import com.example.weatherforecastapplication.data.remote.WeatherForecast
import com.example.weatherforecastapplication.data.remote.WeatherForecastApi
import com.example.weatherforecastapplication.other.ErrorMessages
import com.example.weatherforecastapplication.other.Resource
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader

@ExperimentalCoroutinesApi
class WeatherForecastRepositoryTest {

    private lateinit var weatherForecastRepository: WeatherForecastRepository

    @Mock
    private lateinit var mockDao: WeatherForecastDao

    @Mock
    private lateinit var mockAPI: WeatherForecastApi

    private lateinit var closeable: AutoCloseable

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        weatherForecastRepository = WeatherForecastRepository(mockAPI, mockDao)
    }

    @After
    fun tearDown() {
        closeable.close()
        Mockito.reset(mockDao, mockAPI)
    }

    fun getResourceAsString(fileName: String): String {
        val inputStream = this::class.java.classLoader?.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")
        return BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
    }

    @Test
    fun `fetch weather forecast from API and save to database`() = runTest {

        // Read the JSON data from the file as a mock for response
        val gson = Gson()
        val jsonString = getResourceAsString("WeatherForecast.json")
        val weatherForecast = gson.fromJson(jsonString, WeatherForecast::class.java)

        whenever(mockAPI.getWeatherForecast(any(), any(), any(), any())).thenReturn(
            Response.success(weatherForecast)
        )

        val forecastEntityList = weatherForecastRepository.fromWeatherForecast(weatherForecast)

        // Call repository
        val result = weatherForecastRepository.fetchWeatherForecast(10.0, 20.0)

        //Verify data is inserted into cache
        verify(mockDao).insertAll(forecastEntityList)

        // Assert success result
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `fetchWeatherForecast returns cached data when API fails`() = runTest {
        // Mock API response failure
        whenever(mockAPI.getWeatherForecast(any(), any(), any(), any())).thenReturn(
            Response.error(
                404,
                ResponseBody.create(null, "")
            )
        )
        // Read the JSON data from the file as a mock for response
        val gson = Gson()
        val jsonString = getResourceAsString("WeatherForecast.json")
        val weatherForecast = gson.fromJson(jsonString, WeatherForecast::class.java)
        // Mock cached data
        val cachedData = weatherForecastRepository.fromWeatherForecast(weatherForecast)
        whenever(mockDao.getForecastByCoordinates(any(), any(), any())).thenReturn(cachedData)

        // Call repository
        val result = weatherForecastRepository.fetchWeatherForecast(10.0, 20.0)

        // Assert success with cached data
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(cachedData).isEqualTo((result as Resource.Success).data)
        assertThat(ErrorMessages.DATA_FROM_CACHE).isEqualTo(result.message)
    }

    @Test
    fun `fetchWeatherForecast returns error when API fails and no cached data is available`() =
        runTest {
            // Mock API response failure
            whenever(mockAPI.getWeatherForecast(any(), any(), any(), any())).thenReturn(
                Response.error(
                    404,
                    ResponseBody.create(null, "")
                )
            )

            // Mock no cached data available
            whenever(mockDao.getForecastByCoordinates(any(), any(), any())).thenReturn(emptyList())

            // Call repository
            val result = weatherForecastRepository.fetchWeatherForecast(10.0, 20.0)

            // Assert error result
            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat("Error: Response.error()").isEqualTo(result.message)
        }


    @Test
    fun `fetchWeatherForecast handles network exception and returns cached data`() = runTest {
        // Mock API to throw an exception
        whenever(mockAPI.getWeatherForecast(any(), any(), any(), any())).thenThrow(
            RuntimeException(
                "Network error"
            )
        )

        // Mock cached data
        val gson = Gson()
        val jsonString = getResourceAsString("WeatherForecast.json")
        val weatherForecast = gson.fromJson(jsonString, WeatherForecast::class.java)
        // Mock cached data
        val cachedData = weatherForecastRepository.fromWeatherForecast(weatherForecast)
        whenever(mockDao.getForecastByCoordinates(any(), any(), any())).thenReturn(cachedData)

        // Call repository
        val result = weatherForecastRepository.fetchWeatherForecast(10.0, 20.0)

        // Assert success with cached data
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(cachedData).isEqualTo((result as Resource.Success).data)
        assertThat(ErrorMessages.DATA_FROM_CACHE).isEqualTo(result.message)
    }

}