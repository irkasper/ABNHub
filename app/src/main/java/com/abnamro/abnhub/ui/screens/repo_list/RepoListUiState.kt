package com.abnamro.abnhub.ui.screens.repo_list

import com.abnamro.abnhub.domain.entity.Repo

data class RepoListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lstRepos: List<Repo> = listOf(),
)
