package com.tom.learncoroutinexroom.di.module

import com.tom.learncoroutinexroom.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}