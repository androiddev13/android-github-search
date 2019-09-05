package com.githubsearch.di.module

import com.githubsearch.presentation.githubsearch.GithubSearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeGithubSearchActivity(): GithubSearchActivity

}