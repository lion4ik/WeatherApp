package com.github.lion4ik.dagger

import com.github.lion4ik.domain.UseCaseModule
import com.github.lion4ik.remote.RemoteModule
import com.github.lion4ik.repository.RepositoryModule
import com.github.lion4ik.storage.StorageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NavigationModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteModule::class,
    StorageModule::class])
interface AppComponent {
}