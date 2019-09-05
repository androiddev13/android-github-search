package com.githubsearch.data.model

enum class Status {
    SUCCESS,
    LOADING,
    FAILED
}

data class NetworkStatus private constructor(val status: Status, val message: String? = null) {
    companion object {
        val SUCCESS = NetworkStatus(Status.SUCCESS)
        val LOADING = NetworkStatus(Status.LOADING)
        fun error(msg: String?) = NetworkStatus(Status.FAILED, msg)
    }
}