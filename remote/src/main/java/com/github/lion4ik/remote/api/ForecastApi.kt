package com.github.lion4ik.remote.api

import com.github.lion4ik.remote.model.ForecastApiModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ForecastApi {

    @GET("/{lat},{long}")
    fun getForecast(
        @Path("lat") lat: Double,
        @Path("long") long: Double): Deferred<ForecastApiModel>
}