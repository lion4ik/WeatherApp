package com.github.lion4ik.storage.imnp

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.storage.ForecastStorage
import com.github.lion4ik.storage.ForecastDao
import com.github.lion4ik.storage.model.ForecastDbModel

internal class ForecastStorageImpl(private val forecastDao: ForecastDao): ForecastStorage {

    override suspend fun addForecast(forecast: Forecast) {
        forecastDao.insertForecast(ForecastDbModel.fromForecast(forecast))
    }

    override suspend fun getAllForecasts(): List<Forecast> =
            forecastDao.getForecasts()
}