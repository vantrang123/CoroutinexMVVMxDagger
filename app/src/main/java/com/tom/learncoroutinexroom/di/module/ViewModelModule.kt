package com.tom.learncoroutinexroom.di.module

import androidx.lifecycle.ViewModelProvider
import com.tom.learncoroutinexroom.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}