package com.abnamro.abnhub.ui.screens.repo_detail

import com.abnamro.abnhub.domain.entity.RepoDetail

data class RepoDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val repo: RepoDetail? = null,
)
// TODO: 2022-07-10
// sealed interface RepoDetailUiState {
//    object Loading : RepoDetailUiState
//    data class Data(val repo: RepoDetail) : RepoDetailUiState
//    data class Error(val errorMessage: String) : RepoDetailUiState
// }
