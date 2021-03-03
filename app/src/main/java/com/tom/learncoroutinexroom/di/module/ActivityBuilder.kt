package com.tom.learncoroutinexroom.di.module

import com.tom.learncoroutinexroom.ui.feature.login.SocialLoginActivity
import com.tom.learncoroutinexroom.ui.feature.splash.SplashActivity
import com.tom.learncoroutinexroom.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesSocialLoginActivity(): SocialLoginActivity

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity
}