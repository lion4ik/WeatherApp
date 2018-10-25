package com.github.lion4ik.dagger

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create(Router()) }

    @Provides
    @Singleton
    internal fun provideCiceroneRouter(): Router = cicerone.router

    @Provides
    @Singleton
    internal fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}
