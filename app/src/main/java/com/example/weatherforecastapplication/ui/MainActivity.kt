package com.example.weatherforecastapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapplication.data.local.City
import com.example.weatherforecastapplication.databinding.ActivityMainBinding
import com.example.weatherforecastapplication.other.Resource
import com.example.weatherforecastapplication.other.readJsonFromAssets
import com.example.weatherforecastapplication.viewmodels.WeatherForecastViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import com.example.weatherforecastapplication.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val weatherViewModel: WeatherForecastViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter
    private lateinit var selectedCity: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
        subscribeToObservers()
        setUpDropdownMenu()
        onRetryClick()
    }

    private fun setUpDropdownMenu() {
        val items = getCitiesList()
        selectedCity = items[0]
        var cityNames = items.map { it.cityNameEn }
        val adapter = ArrayAdapter(this, R.layout.list_item_city_name, cityNames)
        binding.tvCityName.setAdapter(adapter)
        binding.tvCityName.setText(cityNames[0], false)
        fetchWeatherForSelectedCity()
        binding.tvCityName.setOnItemClickListener { _, _, position, _ ->
            selectedCity = items[position]
            fetchWeatherForSelectedCity()
        }
    }

    private fun subscribeToObservers() {
        weatherViewModel.weatherForecast.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG)
                            .show()
                    }
                    weatherForecastAdapter.differ.submitList(emptyList()) {
                        weatherForecastAdapter.differ.submitList(response.data)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        showErrorMessage(message)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        weatherForecastAdapter = WeatherForecastAdapter()
        binding.rvCityWeatherForecast.apply {
            adapter = weatherForecastAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun hideProgressBar() {
        binding.prLoading.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.prLoading.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.root.visibility = View.INVISIBLE
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.root.visibility = View.VISIBLE
        binding.itemErrorMessage.errorMessageTextView.text = message
    }

    private fun fetchWeatherForSelectedCity() {
        weatherViewModel.fetchWeather(latitude = selectedCity.lat, longitude = selectedCity.lon)
    }

    private fun getCitiesList(): List<City> {
        val jsonString = readJsonFromAssets(this, "CitiesResponse.json")
        jsonString?.let {
            val gson = Gson()
            val cities: List<City> = gson.fromJson(jsonString, Array<City>::class.java).toList()
            return cities
        } ?: return emptyList()
    }

    private fun onRetryClick() {
        binding.itemErrorMessage.retryButton.setOnClickListener {
            fetchWeatherForSelectedCity()
        }
    }
}