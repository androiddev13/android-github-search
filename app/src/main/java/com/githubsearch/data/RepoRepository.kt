package com.githubsearch.data

import com.githubsearch.data.model.Repository
import io.reactivex.Single

interface RepoRepository {

    fun getRepositories(page: Int, perPage: Int, query: String): Single<List<Repository>>

}