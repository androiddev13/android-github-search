package com.githubsearch.di.module

import androidx.lifecycle.ViewModelProvider
import com.githubsearch.presentation.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}