package com.abnamro.abnhub.test_util

import com.abnamro.abnhub.data.model.Owner
import com.abnamro.abnhub.data.model.RepoDto
import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail

object TestUtil {

    val tRepo = Repo(
        name = "repo1",
        avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4",
        visibility = "public",
        isPrivate = false,
    )

    val tRepoDetail = RepoDetail(
        name = "repo1",
        fullName = "repo1-full-name",
        description = null,
        avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4",
        visibility = "public",
        isPrivate = false,
    )

    val tRepoModel = RepoModel(
        name = "repo1",
        fullName = "repo1-full-name",
        description = null,
        avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4",
        visibility = "public",
        isPrivate = false,
    )

    val tRepoDto1 = RepoDto(
        name = "repo1",
        fullName = "repo1-full-name",
        description = "repo1 description",
        owner = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4"),
        visibility = "public",
        isPrivate = false,
    )

    val tRepoDto2 = RepoDto(
        name = "repo2",
        fullName = "repo2-full-name",
        description = null,
        owner = Owner(avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4"),
        visibility = "public",
        isPrivate = false,
    )

    val tRepoDtoList = listOf(tRepoDto1, tRepoDto2)
}