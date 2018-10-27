package com.github.lion4ik.domain

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.repository.ForecastRepository

class GetAllForecastsUseCase(private val repository: ForecastRepository) {

    suspend fun execute(): List<Forecast> = repository.getAllForecasts()
}