package com.example.weatherforecastapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.data.local.WeatherForecastEntity
import com.example.weatherforecastapplication.databinding.ListItemCityWeatherForecastBinding

class WeatherForecastAdapter():RecyclerView.Adapter<WeatherForecastViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<WeatherForecastEntity>() {
        override fun areItemsTheSame(oldItem: WeatherForecastEntity, newItem: WeatherForecastEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherForecastEntity, newItem: WeatherForecastEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWeatherForecastBinding = ListItemCityWeatherForecastBinding.inflate(inflater, parent, false)
        return WeatherForecastViewHolder(itemWeatherForecastBinding)
    }

    override fun onBindViewHolder(
        holder: WeatherForecastViewHolder,
        position: Int
    ) {
        val weatherForecast = differ.currentList[position]
        holder.bind(weatherForecast)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}