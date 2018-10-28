package com.github.lion4ik.remote.api

import com.github.lion4ik.remote.client.AuthorizationInterceptor
import com.github.lion4ik.remote.dto.ForecastApiModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForecastApi {

    @GET("/${AuthorizationInterceptor.API_KEY_PATH}/{lat},{long}")
    fun getForecast(
        @Path("lat") lat: Double,
        @Path("long") long: Double,
        @Query("lang") lang: String? = null,
        @Query("units") units: String? = null
    ): Deferred<ForecastApiModel>
}