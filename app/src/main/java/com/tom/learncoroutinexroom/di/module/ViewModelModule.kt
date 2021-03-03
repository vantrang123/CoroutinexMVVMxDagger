package com.tom.learncoroutinexroom.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tom.learncoroutinexroom.di.ViewModelKey
import com.tom.learncoroutinexroom.di.factory.ViewModelFactory
import com.tom.learncoroutinexroom.ui.detail.DetailViewModel
import com.tom.learncoroutinexroom.ui.feature.login.SocialLoginViewModel
import com.tom.learncoroutinexroom.ui.feature.splash.SplashViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun providesDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SocialLoginViewModel::class)
    internal abstract fun providesSocialLoginViewModel(viewModel: SocialLoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun providesSplashViewModel(viewModel: SplashViewModel): ViewModel
}