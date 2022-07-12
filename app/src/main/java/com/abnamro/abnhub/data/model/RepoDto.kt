package com.abnamro.abnhub.data.model

import com.google.gson.annotations.SerializedName
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail

/**
 * The received data from API including all blew fields, so I didn't split it into two classes.
 * but in Domain Layer, I split it into two entities: [Repo] and [RepoDetail]
 */
data class RepoDto(
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    val description: String?,

    val owner: Owner,

    val visibility: String,

    @SerializedName("private")
    val isPrivate: Boolean,
)

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
