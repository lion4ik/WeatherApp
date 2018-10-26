package com.github.lion4ik.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.lion4ik.domain.GetForecastUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModelFactory @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel()

        modelClass.isAssignableFrom(ForecastViewModel::class.java) -> ForecastViewModel(getForecastUseCase)

        else -> throw IllegalArgumentException("Unknown model class $modelClass")
    } as T
}
