package com.github.lion4ik.remote

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

internal class RemoteSslSocketFactory {

    companion object {
        private const val DEFAULT_TLS_VERSION = "TLSv1.2"
    }

    fun create(trustManager: X509TrustManager, tlsVersion: String = DEFAULT_TLS_VERSION): SSLSocketFactory {
        val sslContext: SSLContext
        try {
            val trustManagers = arrayOf(trustManager)
            sslContext = SSLContext.getInstance(tlsVersion)
            sslContext.init(null, trustManagers, java.security.SecureRandom())
        } catch (e: KeyManagementException) {
            throw IllegalArgumentException("Can't create SSLContext!", e)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalArgumentException("Can't create SSLContext!", e)
        }

        return sslContext.socketFactory
    }
}
