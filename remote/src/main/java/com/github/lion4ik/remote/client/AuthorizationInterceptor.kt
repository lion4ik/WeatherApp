package com.github.lion4ik.remote.client

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        const val API_KEY_PATH = "API_KEY"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val urlBuilder = original.url().newBuilder()
        val pathSegments = original.url().pathSegments()
        pathSegments.forEachIndexed { i, pathSegment ->
            if (pathSegment == API_KEY_PATH) {
                urlBuilder.setPathSegment(i, apiKey)
            }
        }
        val urlWithApiKey = urlBuilder.build()
        return chain.proceed(original.newBuilder().url(urlWithApiKey).build())
    }
}