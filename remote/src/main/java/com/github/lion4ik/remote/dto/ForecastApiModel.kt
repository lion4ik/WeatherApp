package com.github.lion4ik.remote.dto

import com.github.lion4ik.core.model.Forecast

data class ForecastApiModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val currently: CurrentForecastApiModel,
    val minutely: MinutelyForecastApiModel?
) {

    fun toForecast(): Forecast = Forecast(
        latitude, longitude, timezone, currently.summary, minutely?.summary ?: "",
        currently.temperature, currently.humidity, currently.windSpeed, currently.time
    )
}