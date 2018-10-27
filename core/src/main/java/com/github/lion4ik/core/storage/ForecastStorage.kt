package com.github.lion4ik.core.storage

import com.github.lion4ik.core.model.Forecast

interface ForecastStorage {

    suspend fun addForecast(forecast: Forecast)

    suspend fun getAllForecasts(): List<Forecast>
}