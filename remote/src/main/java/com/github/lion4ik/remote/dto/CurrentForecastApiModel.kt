package com.github.lion4ik.remote.dto

data class CurrentForecastApiModel(
    val summary: String, val icon: String, val temperature: Double,
    val humidity: Double, val windSpeed: Double, val windBearing: Int,
    val time: Long, val minutely: MinutelyForecastApiModel
)