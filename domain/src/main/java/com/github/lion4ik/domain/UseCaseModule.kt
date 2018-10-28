package com.github.lion4ik.domain

import com.github.lion4ik.core.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetForecastUseCase(forecastRepository: ForecastRepository): GetForecastUseCase = GetForecastUseCase(forecastRepository)

    @Singleton
    @Provides
    fun provideGetAllForecastsUseCase(forecastRepository: ForecastRepository): GetAllForecastsUseCase = GetAllForecastsUseCase(forecastRepository)

    @Singleton
    @Provides
    fun provideAddForecastUseCase(forecastRepository: ForecastRepository): AddForecastUseCase = AddForecastUseCase(forecastRepository)
}