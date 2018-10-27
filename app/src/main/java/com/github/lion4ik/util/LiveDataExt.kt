package com.github.lion4ik.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.nonNullObserve(lifecycleOwner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(lifecycleOwner, android.arch.lifecycle.Observer {
        it?.let(observer)
    })
}
