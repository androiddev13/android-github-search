package com.githubsearch.data.model

data class RepositoryOwner(val id: Int, val login: String, val avatarUrl: String) {

    companion object {
        fun getEmpty() = RepositoryOwner(0, "", "")
    }

}