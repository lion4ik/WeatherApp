package com.github.lion4ik.core.error

class ApiException: RuntimeException {
    constructor(): super()
    constructor(cause: Throwable): super(cause)
}