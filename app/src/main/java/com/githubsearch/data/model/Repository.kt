package com.githubsearch.data.model

import java.util.*

data class Repository(val id: Int,
                      val name: String,
                      val fullName: String,
                      val owner: RepositoryOwner,
                      val createdAt: Date)