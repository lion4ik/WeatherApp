package com.github.lion4ik.remote.dto

import com.github.lion4ik.core.model.CurrentForecast

data class CurrentForecastApiModel(val summary: String, val icon: String, val temperature: Double,
                                            val humidity: Double, val windSpeed: Double, val windBearing: Int,
                                            val time: Long){

    fun toCurrentForecast(): CurrentForecast = CurrentForecast(summary, icon, temperature, humidity, windSpeed, windBearing, time)
}