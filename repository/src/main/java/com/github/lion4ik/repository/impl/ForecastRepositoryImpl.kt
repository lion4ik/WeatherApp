package com.github.lion4ik.repository.impl

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.core.repository.ForecastRepository
import com.github.lion4ik.core.storage.ForecastStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class ForecastRepositoryImpl(
    private val remote: ForecastRemote,
    private val storage: ForecastStorage
) : ForecastRepository {

    override suspend fun getAllForecasts(): List<Forecast> = coroutineScope {
        async(Dispatchers.IO) { storage.getAllForecasts() }.await()
    }

    override suspend fun getForecastRemote(lat: Double, long: Double, lang: String?, units: String?): Forecast =
        coroutineScope {
            val forecast = remote.getForecast(lat, long, lang, units)
            async(Dispatchers.IO) { storage.addForecast(forecast) }.await()
            forecast
        }
}