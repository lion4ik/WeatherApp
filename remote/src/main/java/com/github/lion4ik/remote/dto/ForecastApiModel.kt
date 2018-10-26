package com.github.lion4ik.remote.dto

import com.github.lion4ik.core.model.Forecast

data class ForecastApiModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: CurrentForecastApiModel
) {

    fun toForecast(): Forecast = Forecast(latitude, longitude, timezone, currently.toCurrentForecast())
}