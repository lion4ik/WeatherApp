package com.github.lion4ik.core.model

data class Forecast(val latitude: Double, val longitude: Double, val timezone: String,
               val currently: CurrentForecast)