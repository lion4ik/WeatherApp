package com.github.lion4ik

import android.app.Application
import com.github.lion4ik.dagger.AppComponent
import com.github.lion4ik.dagger.AppModule
import com.github.lion4ik.dagger.DaggerAppComponent
import com.github.lion4ik.remote.RemoteModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        @Suppress("DEPRECATION")
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .remoteModule(RemoteModule(BuildConfig.API_BASE_URL, BuildConfig.API_ALLOW_ANY_CERT, true))
            .build()
    }
}