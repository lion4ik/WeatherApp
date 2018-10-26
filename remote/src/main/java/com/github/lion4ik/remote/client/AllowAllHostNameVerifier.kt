package com.imagenpay.app.remote.misc

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

internal class AllowAllHostNameVerifier : HostnameVerifier {

    override fun verify(hostname: String, session: SSLSession): Boolean = true
}
