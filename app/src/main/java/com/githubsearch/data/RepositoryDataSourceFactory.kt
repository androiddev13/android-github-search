package com.githubsearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.SearchQuery
import io.reactivex.disposables.CompositeDisposable

class RepositoryDataSourceFactory constructor(private val repoRepository: RepoRepository,
                                              private val compositeDisposable: CompositeDisposable): DataSource.Factory<Long, Repository>() {

    var query = SearchQuery.empty

    private val _repositoryDataSourceLiveData = MutableLiveData<RepositoryDataSource>()
    val repositoryDataSourceLiveData: LiveData<RepositoryDataSource>
        get() = _repositoryDataSourceLiveData

    override fun create(): DataSource<Long, Repository> {
        val repoDataSource = RepositoryDataSource(repoRepository, compositeDisposable, query)
        _repositoryDataSourceLiveData.postValue(repoDataSource)
        return repoDataSource
    }
}