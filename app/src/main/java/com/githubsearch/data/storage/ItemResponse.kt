package com.githubsearch.data.storage

import com.githubsearch.data.model.Repository
import com.githubsearch.data.model.RepositoryOwner
import com.githubsearch.fromISO8601ToUtc
import com.google.gson.annotations.SerializedName
import java.util.*

data class ItemResponse (@SerializedName("id") val id: Int,
                         @SerializedName("name") var name: String,
                         @SerializedName("full_name") var fullName: String,
                         @SerializedName("owner") val owner: OwnerResponse?,
                         @SerializedName("updated_at") val updatedAt: String?) {

    fun toRepository() = Repository(id, name, fullName,
        owner?.toRepositoryOwner()?: RepositoryOwner.getEmpty(), updatedAt?.fromISO8601ToUtc()?:Calendar.getInstance().time)

}
