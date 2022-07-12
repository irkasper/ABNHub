package com.abnamro.abnhub.domain.entity

data class RepoDetail(
    val name: String,
    val fullName: String,
    val description: String?,
    val avatarUrl: String,
    val visibility: String,
    val isPrivate: Boolean,
)
