package com.github.lion4ik.storage.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.github.lion4ik.core.model.Forecast

@Entity(tableName = "forecast")
data class ForecastDbModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val summary: String,
    val nearestForecastSummary: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val time: Long,
    @PrimaryKey(autoGenerate = true) val forecastId: Int = 0
) {

    companion object {
        fun fromForecast(forecast: Forecast): ForecastDbModel = ForecastDbModel(
            forecast.latitude,
            forecast.longitude,
            forecast.timezone,
            forecast.summary,
            forecast.nearestForecastSummary,
            forecast.temperature,
            forecast.humidity,
            forecast.windSpeed,
            forecast.time
        )
    }

    fun toForecast(): Forecast = Forecast(
        latitude, longitude, timezone,
        summary, nearestForecastSummary, temperature, humidity, windSpeed, time
    )
}