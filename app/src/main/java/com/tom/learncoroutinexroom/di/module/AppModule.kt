package com.tom.learncoroutinexroom.di.module

import android.content.Context
import com.tom.learncoroutinexroom.Application
import com.tom.learncoroutinexroom.data.PlayerRepository
import com.tom.learncoroutinexroom.data.remote.PlayerRemoteDataSource
import com.tom.learncoroutinexroom.data.remote.Service
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideApplication(app: Application): Application = app

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)

    @Provides
    @Singleton
    @Named("IO")
    fun provideBackgroundDispatchers(): CoroutineDispatcher =
        Dispatchers.IO

    @Provides
    @Singleton
    @Named("MAIN")
    fun provideMainDispatchers(): CoroutineDispatcher =
        Dispatchers.Main

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: Service) = PlayerRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providePlayerRepository(remote: PlayerRemoteDataSource) =
        PlayerRepository(remote)
}