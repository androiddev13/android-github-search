package com.githubsearch.data.storage

import com.githubsearch.data.model.RepositoryOwner
import com.google.gson.annotations.SerializedName

data class OwnerResponse(@SerializedName("id") val id: Int,
                         @SerializedName("login") val login: String,
                         @SerializedName("avatar_url") val avatarUrl: String?) {

    fun toRepositoryOwner() = RepositoryOwner(id, login, avatarUrl?:"")

}
