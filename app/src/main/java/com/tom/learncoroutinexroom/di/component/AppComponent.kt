package com.tom.learncoroutinexroom.di.component

import com.tom.learncoroutinexroom.Application
import com.tom.learncoroutinexroom.di.module.*
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
        NetworkModule::class,
        AppModule::class,
        ViewModelModule::class,
        FragmentDialogModule::class
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