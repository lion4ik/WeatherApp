package com.github.lion4ik.util

import android.arch.lifecycle.ViewModel
import com.github.lion4ik.R
import com.github.lion4ik.core.error.ApiException
import com.github.lion4ik.core.error.NoInternetException

fun ViewModel.getCommonErrorDescription(e: Throwable): Int = when (e) {
    is ApiException -> R.string.error_api
    is NoInternetException -> R.string.error_no_internet
    else -> R.string.error_unknown
}