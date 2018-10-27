package com.github.lion4ik.viewmodel.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.lion4ik.misc.SingleLiveData
import kotlinx.coroutines.launch

abstract class SingleDataLoadingViewModel<Params, Result> : ScopedViewModel() {

    private val isLoadingMutable = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingMutable

    private val isEmptyMutable = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = isEmptyMutable

    private val errorMutable = SingleLiveData<Int>()
    val error: LiveData<Int> = errorMutable

    private val resultMutable = MutableLiveData<Result>()

    protected abstract suspend fun loadData(params: Params, refresh: Boolean = false): Result

    protected abstract fun isResultEmpty(result: Result): Boolean

    protected abstract fun getErrorDescription(error: Throwable): Int

    protected fun getRefreshableResult(
        params: Params,
        refresh: Boolean = false,
        cancelOngoing: Boolean = false
    ): LiveData<Result> {
        if (cancelOngoing) {
            isLoadingMutable.value = false
            job.cancel()
        }

        val shouldLoadData = resultMutable.value == null || refresh || cancelOngoing
        if (shouldLoadData) startDataLoading(params, refresh)
        return resultMutable
    }

    private fun startDataLoading(params: Params, refresh: Boolean = false) {
        if (isLoadingMutable.value == true) {
            return
        }

        isLoadingMutable.value = true

        launch(context = coroutineContext) {
            try {
                val loadResult = loadData(params, refresh)
                resultMutable.value = loadResult
                isEmptyMutable.value = isResultEmpty(loadResult)
                isLoadingMutable.value = false
            } catch (e: Throwable) {
                errorMutable.value = getErrorDescription(e)
                val result = resultMutable.value
                isEmptyMutable.value = result == null || isResultEmpty(result)
                isLoadingMutable.value = false
            }
        }
    }
}
