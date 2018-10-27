package com.github.lion4ik.storage

import android.arch.persistence.room.Room
import android.content.Context
import com.github.lion4ik.core.storage.ForecastStorage
import com.github.lion4ik.storage.imnp.ForecastStorageImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideForecastDatabase(): PrometheusDatabase =
        Room.databaseBuilder(
            appContext,
            PrometheusDatabase::class.java,
            PrometheusDatabase.DB_FILE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideForecastStorage(prometheusDb: PrometheusDatabase): ForecastStorage = ForecastStorageImpl(prometheusDb.getForecastDao())
}