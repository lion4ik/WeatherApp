package com.github.lion4ik.viewmodel

import android.arch.lifecycle.LiveData
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.domain.GetAllForecastsUseCase
import com.github.lion4ik.navigation.AddLocationScreen
import com.github.lion4ik.util.getCommonErrorDescription
import com.github.lion4ik.viewmodel.base.SingleDataLoadingViewModel
import ru.terrakok.cicerone.Router

class ForecastsViewModel(private val router: Router,
                         private val getAllForecastsUseCase: GetAllForecastsUseCase): SingleDataLoadingViewModel<Unit, List<Forecast>>() {

    override suspend fun loadData(params: Unit, refresh: Boolean): List<Forecast> =
        getAllForecastsUseCase.execute()

    override fun isResultEmpty(result: List<Forecast>): Boolean = result.isEmpty()

    override fun getErrorDescription(error: Throwable): Int = getCommonErrorDescription(error)

    fun onAddLocationClicked() = router.navigateTo(AddLocationScreen())

    fun getAllForecasts(): LiveData<List<Forecast>> = getRefreshableResult(Unit, true)
}