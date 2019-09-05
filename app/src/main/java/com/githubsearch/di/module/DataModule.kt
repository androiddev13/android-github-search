package com.githubsearch.di.module

import com.githubsearch.data.DefaultRepoRepository
import com.githubsearch.data.RepoRepository
import com.githubsearch.data.RepositoryDataSource
import com.githubsearch.data.storage.GithubApiService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRepoRepository(api: GithubApiService): RepoRepository {
        return DefaultRepoRepository(api)
    }

}