package com.github.lion4ik.repository.impl

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.core.repository.ForecastRepository

internal class ForecastRepositoryImpl(private val remote: ForecastRemote) : ForecastRepository {

    override suspend fun getSavedForecast(lat: Double, long: Double, lang: String?, units: String?): Forecast {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getForecastRemote(lat: Double, long: Double, lang: String?, units: String?): Forecast =
        remote.getForecast(lat, long, lang, units)
}