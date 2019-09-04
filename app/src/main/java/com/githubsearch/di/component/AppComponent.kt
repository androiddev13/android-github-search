package com.githubsearch.di.component

import android.app.Application
import com.githubsearch.AndroidApplication
import com.githubsearch.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, NetworkModule::class,
    ViewModelFactoryModule::class, ViewModelModule::class, DataModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: AndroidApplication)

}