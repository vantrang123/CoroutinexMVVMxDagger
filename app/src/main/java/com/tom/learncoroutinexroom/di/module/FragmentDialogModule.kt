package com.tom.learncoroutinexroom.di.module

import com.tom.learncoroutinexroom.ui.detail.DetailDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentDialogModule {
    @ContributesAndroidInjector
    abstract fun contributesDetailDialogFragment(): DetailDialogFragment
}