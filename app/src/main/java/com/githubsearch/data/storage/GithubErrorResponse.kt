package com.githubsearch.data.storage

import com.google.gson.annotations.SerializedName

data class GithubErrorResponse(@SerializedName("message") val message: String,
                               @SerializedName("documentation_url") val documentationUrl: String)