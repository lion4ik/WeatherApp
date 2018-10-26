package com.github.lion4ik.remote.impl

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.remote.api.ForecastApi

internal class ForecastRemoteImpl(private val forecastApi: ForecastApi) : ForecastRemote {

    override suspend fun getForecast(lat: Double, long: Double, lang: String?, units: String?): Forecast =
        forecastApi.getForecast(lat, long, lang, units)
            .await()
            .toForecast()
}