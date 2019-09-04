package com.githubsearch.data.storage

import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.RepositoryOwner
import com.google.gson.annotations.SerializedName
import java.util.*

data class ItemResponse (@SerializedName("id") val id: Int,
                         @SerializedName("name") var name: String,
                         @SerializedName("full_name") var fullName: String,
                         @SerializedName("owner") val owner: OwnerResponse?,
                         @SerializedName("created_at") val createdAt: String?) {

    // TODO convert date properly
    fun toRepository() = Repository(id, name, fullName,
        owner?.toRepositoryOwner()?: RepositoryOwner.getEmpty(), Calendar.getInstance().time)

}
