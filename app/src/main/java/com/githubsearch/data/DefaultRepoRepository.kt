package com.githubsearch.data

import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.SearchQuery
import com.githubsearch.data.storage.GithubApiService
import com.githubsearch.data.storage.getFormattedSearchQuery
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultRepoRepository constructor(private val api: GithubApiService) : RepoRepository {

    override fun getRepositories(page: Long, perPage: Int, searchQuery: SearchQuery): Single<List<Repository>> {
        return api.getRepositories(page, perPage, searchQuery.getFormattedSearchQuery())
            .map { repositoriesResponse ->
                repositoriesResponse.items
                    ?. map { it.toRepository() }
                    ?: listOf()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}