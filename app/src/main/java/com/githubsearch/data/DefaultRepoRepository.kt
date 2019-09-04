package com.githubsearch.data

import com.githubsearch.data.model.Repository
import com.githubsearch.data.storage.GithubApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultRepoRepository constructor(private val api: GithubApiService) : RepoRepository {

    override fun getRepositories(page: Int, perPage: Int, query: String): Single<List<Repository>> {
        return api.getRepositories(page, perPage, query)
            .map { repositoriesResponse ->
                repositoriesResponse.items
                    ?. map { it.toRepository() }
                    ?: listOf()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}