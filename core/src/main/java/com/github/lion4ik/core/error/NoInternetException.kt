package com.github.lion4ik.core.error

class NoInternetException: RuntimeException {
    constructor(): super()
    constructor(cause: Throwable): super(cause)
}