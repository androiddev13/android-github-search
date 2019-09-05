package com.githubsearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.githubsearch.data.model.NetworkStatus
import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.SearchQuery
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class RepositoryDataSource constructor(private val repoRepository: RepoRepository,
                                       private val compositeDisposable: CompositeDisposable,
                                       private val searchQuery: SearchQuery) : PageKeyedDataSource<Long, Repository>() {

    private val _networkStatusLiveData = MutableLiveData<NetworkStatus>()
    val networkStatusLiveData: LiveData<NetworkStatus>
        get() = _networkStatusLiveData

    private val _initialLoadLiveData = MutableLiveData<NetworkStatus>()
    val initialLoadLiveData: LiveData<NetworkStatus>
        get() = _initialLoadLiveData

    private var retryCompletable: Completable? = null

    fun retry() {
        retryCompletable?.let {
            val disposable = it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
            compositeDisposable.add(disposable)
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>,
                             callback: LoadInitialCallback<Long, Repository>) {
        _initialLoadLiveData.postValue(NetworkStatus.LOADING)
        val disposable = repoRepository.getRepositories(1, params.requestedLoadSize, searchQuery).subscribe({
            setRetry(null)
            _initialLoadLiveData.postValue(NetworkStatus.SUCCESS)
            callback.onResult(it, 1, 2)
        }, {
            setRetry(Action {loadInitial(params, callback)} )
            _initialLoadLiveData.postValue(NetworkStatus.error(it.message))
        })
        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {
        _networkStatusLiveData.postValue(NetworkStatus.LOADING)
        val disposable = repoRepository.getRepositories(params.key, params.requestedLoadSize, searchQuery).subscribe({
            setRetry(null)
            _networkStatusLiveData.postValue(NetworkStatus.SUCCESS)
            val nextPage = if (it.isNotEmpty()) params.key + 1 else null
            callback.onResult(it, nextPage)
        }, {
            setRetry(Action {loadAfter(params, callback)})
            _networkStatusLiveData.postValue(NetworkStatus.error(it.message))
        })
        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {
        // TODO stub
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action != null) Completable.fromAction(action) else null
    }
}