package com.github.lion4ik.remote.impl

import com.github.lion4ik.core.error.ApiException
import com.github.lion4ik.core.error.NoInternetException
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.remote.api.ForecastApi
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

internal class ForecastRemoteImpl(private val forecastApi: ForecastApi) : ForecastRemote {

    override suspend fun getForecast(lat: Double, long: Double, lang: String?, units: String?): Forecast =
        try {
            forecastApi.getForecast(lat, long, lang, units)
                .await()
                .toForecast()
        } catch (e: HttpException) {
            throw ApiException(e)
        } catch (e: IOException) {
            throw NoInternetException(e)
        } catch (e: SocketTimeoutException) {
            throw NoInternetException(e)
        }
}