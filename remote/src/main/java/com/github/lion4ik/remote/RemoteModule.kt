package com.github.lion4ik.remote

import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.remote.api.ForecastApi
import com.github.lion4ik.remote.impl.ForecastRemoteImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imagenpay.app.remote.misc.AllowAllHostNameVerifier
import com.imagenpay.app.remote.misc.AllowAllTrustManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
class RemoteModule(private val baseUrl: String,
                   private val allowUntrustedConnection: Boolean,
                   private val fullLog: Boolean) {

    companion object {
        private const val CONNECT_TIMEOUT = 20L
        private const val READ_TIMEOUT = 30L
        private const val WRITE_TIMEOUT = 30L
    }

    @Singleton
    @Provides
    fun provideTrustManager(): X509TrustManager = AllowAllTrustManager()

    @Singleton
    @Provides
    fun provideHostNameVerifier(): HostnameVerifier = AllowAllHostNameVerifier()

    @Singleton
    @Provides
    fun provideSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory = RemoteSslSocketFactory().create(trustManager)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(trustManager: X509TrustManager,
                            hostNameVerifier: HostnameVerifier,
                            sslSocketFactory: SSLSocketFactory): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (fullLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)

        if(allowUntrustedConnection){
            builder
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(hostNameVerifier)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideForecastApi(retrofit: Retrofit): ForecastApi = retrofit.create(ForecastApi::class.java)

    @Singleton
    @Provides
    fun provideForecastRemote(forecastApi: ForecastApi): ForecastRemote = ForecastRemoteImpl(forecastApi)
}