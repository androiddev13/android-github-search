package com.githubsearch.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.githubsearch.data.RepoRepository
import com.githubsearch.data.RepositoryDataSourceFactory
import com.githubsearch.data.model.*
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GithubSearchViewModel @Inject constructor(repoRepository: RepoRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val repositoryDataSourceFactory = RepositoryDataSourceFactory(repoRepository, compositeDisposable)

    private val _repositoriesLiveData: LiveData<PagedList<Repository>>
    val repositoriesLiveData: LiveData<PagedList<Repository>>
        get() = _repositoriesLiveData

    private val _networkStatusLiveData = Transformations.switchMap(repositoryDataSourceFactory.repositoryDataSourceLiveData) {
        it.networkStatusLiveData
    }
    val networkStatusLiveData: LiveData<NetworkStatus>
        get() = _networkStatusLiveData

    private val _initialLoadStatusLiveData = Transformations.switchMap(repositoryDataSourceFactory.repositoryDataSourceLiveData) {
        it.initialLoadLiveData
    }
    val initialLoadStatusLiveData: LiveData<NetworkStatus>
        get() = _initialLoadStatusLiveData

    private val queryLiveData = MutableLiveData<SearchQuery>()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .build()
        _repositoriesLiveData = Transformations.switchMap<SearchQuery, PagedList<Repository>>(queryLiveData) {
            repositoryDataSourceFactory.query = it
            LivePagedListBuilder(repositoryDataSourceFactory, config).build()
        }
    }

    fun retry() {
        repositoryDataSourceFactory.repositoryDataSourceLiveData.value?.retry()
    }

    fun refresh() {
        repositoryDataSourceFactory.repositoryDataSourceLiveData.value?.invalidate()
    }

    fun setQuery(query: String) {
        queryLiveData.value = SearchQuery(query, SearchQueryTarget.NAME)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}