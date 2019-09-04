package com.githubsearch.data.storage

import com.githubsearch.data.model.RepositoryOwner
import com.google.gson.annotations.SerializedName

data class OwnerResponse(@SerializedName("id") val id: Int,
                         @SerializedName("avatar_url") val avatarUrl: String?) {

    fun toRepositoryOwner() = RepositoryOwner(id, avatarUrl?:"")

}
