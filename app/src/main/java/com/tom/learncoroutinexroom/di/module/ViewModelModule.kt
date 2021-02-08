package com.tom.learncoroutinexroom.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tom.learncoroutinexroom.di.ViewModelKey
import com.tom.learncoroutinexroom.di.factory.ViewModelFactory
import com.tom.learncoroutinexroom.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun providesPlayerViewModel(viewModel: MainViewModel): ViewModel
}