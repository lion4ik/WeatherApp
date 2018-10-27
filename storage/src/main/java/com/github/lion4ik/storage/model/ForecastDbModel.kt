package com.github.lion4ik.storage.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.github.lion4ik.core.model.CurrentForecast
import com.github.lion4ik.core.model.Forecast

@Entity(tableName = "forecast")
data class ForecastDbModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val summary: String,
    val icon: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val windBearing: Int,
    val time: Long,
    @PrimaryKey(autoGenerate = true) val forecastId: Int = 0
) {

    companion object {
        fun fromForecast(forecast: Forecast): ForecastDbModel = ForecastDbModel(
            forecast.latitude,
            forecast.longitude,
            forecast.timezone,
            forecast.currently.summary,
            forecast.currently.icon,
            forecast.currently.temperature,
            forecast.currently.humidity,
            forecast.currently.windSpeed,
            forecast.currently.windBearing,
            forecast.currently.time
        )
    }

    fun toForecast(): Forecast = Forecast(
        latitude, longitude, timezone,
        CurrentForecast(summary, icon, temperature, humidity, windSpeed, windBearing, time)
    )
}