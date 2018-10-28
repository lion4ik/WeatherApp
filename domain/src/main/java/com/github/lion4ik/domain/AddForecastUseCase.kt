package com.github.lion4ik.domain

import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.repository.ForecastRepository

class AddForecastUseCase(private val repository: ForecastRepository) {

    suspend fun execute(forecast: Forecast) =
            repository.addForecast(forecast)
}