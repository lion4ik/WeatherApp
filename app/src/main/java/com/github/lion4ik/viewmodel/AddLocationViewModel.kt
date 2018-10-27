package com.github.lion4ik.viewmodel

import android.arch.lifecycle.LiveData
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.domain.GetForecastUseCase
import com.github.lion4ik.util.getCommonErrorDescription
import com.github.lion4ik.viewmodel.base.SingleDataLoadingViewModel
import ru.terrakok.cicerone.Router

class AddLocationViewModel(private val router: Router,
                           private val forecastUseCase: GetForecastUseCase) :
    SingleDataLoadingViewModel<AddLocationViewModel.ForecastParams, Forecast>() {

    override suspend fun loadData(params: ForecastParams, refresh: Boolean): Forecast =
        forecastUseCase.execute(params.lat, params.long, params.lang, params.units)

    override fun isResultEmpty(result: Forecast): Boolean = false

    override fun getErrorDescription(error: Throwable): Int = getCommonErrorDescription(error)

    fun getLocationForecast(params: ForecastParams, refresh: Boolean = true): LiveData<Forecast> = getRefreshableResult(params, refresh)

    class ForecastParams(val lat: Double, val long: Double, val lang: String? = null, val units: String? = null)
}