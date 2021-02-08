package com.tom.learncoroutinexroom.di.component

import com.tom.learncoroutinexroom.Application
import com.tom.learncoroutinexroom.di.module.ActivityBuilder
import com.tom.learncoroutinexroom.di.module.AppModule
import com.tom.learncoroutinexroom.di.module.NetworkModule
import com.tom.learncoroutinexroom.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        ViewModelModule::class,
        NetworkModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<Application> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}