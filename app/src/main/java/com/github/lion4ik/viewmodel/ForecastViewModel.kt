package com.github.lion4ik.viewmodel

import android.arch.lifecycle.LiveData
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.domain.GetForecastUseCase
import com.github.lion4ik.misc.SingleLiveData
import com.github.lion4ik.viewmodel.base.ScopedViewModel
import kotlinx.coroutines.launch

class ForecastViewModel(private val forecastUseCase: GetForecastUseCase) : ScopedViewModel() {

    private val forecastMutable: SingleLiveData<Forecast> = SingleLiveData()
    val forecast: LiveData<Forecast> = forecastMutable

    fun getForecast(lat: Double, long: Double, lang: String? = null, units: String? = null) = launch(context = coroutineContext) {
        val f = forecastUseCase.execute(lat, long, lang, units)
        forecastMutable.value = f
    }
}