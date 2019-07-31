package com.github.lion4ik.remote.client

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

internal class AllowAllHostNameVerifier : HostnameVerifier {

    override fun verify(hostname: String, session: SSLSession): Boolean = true
}
