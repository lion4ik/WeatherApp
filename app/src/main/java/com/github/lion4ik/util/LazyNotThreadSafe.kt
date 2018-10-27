package com.github.lion4ik.util

fun <T> lazyNotThreadSafe(initializer: () -> T): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE, initializer)
