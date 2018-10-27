package com.github.lion4ik.core.repository

import com.github.lion4ik.core.model.Forecast

interface ForecastRepository {

    suspend fun getForecastRemote(lat: Double, long: Double, lang: String? = null, units: String? = null): Forecast

    suspend fun getAllForecasts(): List<Forecast>

}