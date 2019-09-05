package com.githubsearch.data.storage

import com.githubsearch.data.model.SearchQuery
import com.githubsearch.data.model.SearchQueryTarget

fun SearchQuery.getFormattedSearchQuery(): String {
    return when (target) {
        SearchQueryTarget.NAME -> "$query+in:name"
        SearchQueryTarget.DEFAULT -> query
    }
}