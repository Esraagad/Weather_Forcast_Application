package com.example.weatherforecastapplication.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.data.local.WeatherForecastEntity
import com.example.weatherforecastapplication.databinding.ListItemCityWeatherForecastBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class WeatherForecastViewHolder(private val binding: ListItemCityWeatherForecastBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: WeatherForecastEntity) {
        binding.tvCityName.text = forecast.cityName
        binding.tvTemp.text = forecast.temperature.toInt().toString().plus("°C")
        binding.tvDate.text = dateFormatter(forecast.date)
        binding.tvDescription.text = forecast.description
        binding.tvMaxTemp.text = forecast.maxTemperature.toInt().toString().plus("°C")
        binding.tvMinTemp.text = forecast.minTemperature.toInt().toString().plus("°C")
    }

    fun dateFormatter(date: Int): String {
        val timestampMillis = date.toLong() * 1000
        val date = Date(timestampMillis)
        val sdf = SimpleDateFormat("EEEE, dd hh:mm a", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val formattedDate = sdf.format(date)
        return formattedDate
    }

}