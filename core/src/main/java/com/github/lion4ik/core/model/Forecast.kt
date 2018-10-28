package com.github.lion4ik.core.model

data class Forecast(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val summary: String,
    val nearestForecastSummary: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val time: Long
)