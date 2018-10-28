package com.github.lion4ik.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.domain.AddForecastUseCase
import com.github.lion4ik.domain.GetForecastUseCase
import com.github.lion4ik.misc.SingleLiveData
import com.github.lion4ik.util.getCommonErrorDescription
import com.github.lion4ik.viewmodel.base.ScopedViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router

class AddLocationViewModel(
    private val router: Router,
    private val forecastUseCase: GetForecastUseCase,
    private val addForecastUseCase: AddForecastUseCase
) :
    ScopedViewModel() {

    companion object {
        val DEFAULT_LOCATION = AddLocationViewModel.ForecastParams(51.500334, -0.085013)
    }

    private val locationSelectedMutable: SingleLiveData<LatLng> = SingleLiveData()
    val locationSelected: LiveData<LatLng> = locationSelectedMutable

    private val forecastForCurrentLocationMutable: SingleLiveData<Forecast> = SingleLiveData()
    val forecastForCurrentLocation: LiveData<Forecast> = forecastForCurrentLocationMutable

    private val forecastErrorMutable: MutableLiveData<Int> = MutableLiveData()
    val forecastError: LiveData<Int> = forecastErrorMutable

    fun getLocationForecast(params: ForecastParams) {
        launch(context = coroutineContext) {
            try {
                forecastForCurrentLocationMutable.value =
                        forecastUseCase.execute(params.lat, params.long, params.lang, params.units)
            } catch (e: Throwable) {
                forecastErrorMutable.value = getCommonErrorDescription(e)
            }
        }
    }

    fun onSelectedLocation() {
        launch(coroutineContext) {
            forecastForCurrentLocation.value?.let { forecast ->
                addForecastUseCase.execute(forecast)
                router.exit()
            }
        }
    }

    fun onLocationClicked(location: LatLng) {
        locationSelectedMutable.value = location
    }

    class ForecastParams(val lat: Double, val long: Double, val lang: String? = null, val units: String = "si")
}