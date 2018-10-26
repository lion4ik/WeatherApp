package com.github.lion4ik.core.model

data class CurrentForecast(val summary: String, val icon: String, val temperature: Double,
                      val humidity: Double, val windSpeed: Double, val windBearing: Int,
                      val time: Long)