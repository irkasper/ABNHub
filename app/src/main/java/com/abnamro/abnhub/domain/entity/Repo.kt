package com.abnamro.abnhub.domain.entity


data class Repo(
    val name: String,
    val avatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean,
)
