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
    fun provideForecastDatabase(): ForecastDatabase =
        Room.databaseBuilder(
            appContext,
            ForecastDatabase::class.java,
            ForecastDatabase.DB_FILE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideForecastStorage(forecastDb: ForecastDatabase): ForecastStorage = ForecastStorageImpl(forecastDb.getForecastDao())
}