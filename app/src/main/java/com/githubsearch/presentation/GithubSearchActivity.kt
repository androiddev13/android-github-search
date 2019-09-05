package com.githubsearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.githubsearch.R
import com.githubsearch.data.model.NetworkStatus
import com.githubsearch.data.model.Status
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_github_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GithubSearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_search)
        initView()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubSearchViewModel::class.java)
        viewModel.repositoriesLiveData.observe(this, Observer {
            (recyclerView.adapter as RepositoryAdapter).setData(it, viewModel.queryLiveData.value?.query?:"")
        })
        viewModel.networkStatusLiveData.observe(this, Observer {
            (recyclerView.adapter as RepositoryAdapter).setNetworkState(it)
        })
        viewModel.initialLoadStatusLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> swipeRefreshLayout.isRefreshing = true
                Status.SUCCESS -> swipeRefreshLayout.isRefreshing = false
                Status.FAILED -> swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = RepositoryAdapter { viewModel.retry() }

        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

        RxSearchView.queryTextChanges(searchView)
            .debounce(1, TimeUnit.SECONDS)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewModel.setQuery(it.toString())
            }
    }

}
