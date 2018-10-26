package com.github.lion4ik.core.remote

import com.github.lion4ik.core.model.Forecast

interface ForecastRemote {

    suspend fun getForecast(lat: Double, long: Double, lang: String? = null, units: String? = null): Forecast
}
