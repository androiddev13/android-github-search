package com.githubsearch.di.module

import androidx.lifecycle.ViewModel
import com.githubsearch.di.ViewModelKey
import com.githubsearch.presentation.githubsearch.GithubSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GithubSearchViewModel::class)
    abstract fun bindGithubSearchViewModel(githubSearchViewModel: GithubSearchViewModel): ViewModel

}