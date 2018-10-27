package com.github.lion4ik.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.lion4ik.domain.GetAllForecastsUseCase
import com.github.lion4ik.domain.GetForecastUseCase
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModelFactory @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val getAllForecastsUseCase: GetAllForecastsUseCase,
    private val router: Router
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel()

        modelClass.isAssignableFrom(ForecastsViewModel::class.java) -> ForecastsViewModel(router, getAllForecastsUseCase)

        modelClass.isAssignableFrom(AddLocationViewModel::class.java) -> AddLocationViewModel(router, getForecastUseCase)

        else -> throw IllegalArgumentException("Unknown model class $modelClass")
    } as T
}
