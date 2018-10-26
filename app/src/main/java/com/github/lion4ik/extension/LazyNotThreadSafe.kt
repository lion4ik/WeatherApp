package com.github.lion4ik.extension

fun <T> lazyNotThreadSafe(initializer: () -> T): Lazy<T> =
        lazy(LazyThreadSafetyMode.NONE, initializer)
