package com.github.lion4ik.repository

import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.core.repository.ForecastRepository
import com.github.lion4ik.repository.impl.ForecastRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideForecastRepository(forecastRemote: ForecastRemote): ForecastRepository = ForecastRepositoryImpl(forecastRemote)
}