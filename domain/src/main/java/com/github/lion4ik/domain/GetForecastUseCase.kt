package com.github.lion4ik.domain

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.repository.ForecastRepository

class GetForecastUseCase(private val repository: ForecastRepository) {

    suspend fun execute(lat: Double, long: Double, lang: String? = null, units: String? = null): Forecast =
        repository.getForecastRemote(lat, long, lang, units)
}