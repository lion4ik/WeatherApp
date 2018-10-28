package com.github.lion4ik.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ToolbarBackButtonViewModel : ViewModel() {

    private val showToolbarBackButtonMutable: MutableLiveData<Boolean> = MutableLiveData()
    val showToolbarBackButton: LiveData<Boolean> = showToolbarBackButtonMutable

    fun setToolbarBackButtonVisibility(isVisible: Boolean) {
        showToolbarBackButtonMutable.value = isVisible
    }
}