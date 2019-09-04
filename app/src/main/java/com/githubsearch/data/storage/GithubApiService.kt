package com.githubsearch.data.storage

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    fun getRepositories(@Query("page") page: Int,
                        @Query("per_page") perPage: Int,
                        @Query("q") query: String): Single<RepositoriesResponse>

}