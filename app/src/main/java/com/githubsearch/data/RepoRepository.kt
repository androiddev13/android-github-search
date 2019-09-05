package com.githubsearch.data

import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.SearchQuery
import io.reactivex.Single

interface RepoRepository {

    fun getRepositories(page: Long, perPage: Int, searchQuery: SearchQuery): Single<List<Repository>>

}