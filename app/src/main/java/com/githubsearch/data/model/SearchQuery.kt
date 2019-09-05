package com.githubsearch.data.model

enum class SearchQueryTarget {
    NAME, DEFAULT
}

data class SearchQuery(val query: String, val target: SearchQueryTarget) {
    companion object {
        val empty = SearchQuery("", SearchQueryTarget.DEFAULT)
    }
}