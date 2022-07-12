package com.abnamro.abnhub.ui.screens.repo_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abnamro.abnhub.R
import com.abnamro.abnhub.domain.screens.repo_detail.GetRepoDetailUseCase
import com.abnamro.abnhub.utils.ResourcesProvider
import com.abnamro.abnhub.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * the viewmodel to provide uiState for the fragment and updateData function to update data on demand
 *
 * all functionalities of UI layer implemented in viewmodel to release the fragment from managing state
 */
@HiltViewModel
class RepoDetailViewModel @Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepoDetailUseCase: GetRepoDetailUseCase,
    resourcesProvider: ResourcesProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val repoName = savedStateHandle.get<String>(REPO_NAME_SAVED_STATE_KEY)
            ?: "".also { _uiState.update { it.copy(errorMessage = resourcesProvider.getString(R.string.error_getting_reponame)) } }

        viewModelScope.launch {
            getRepoDetail(repoName)
        }
    }

    private suspend fun getRepoDetail(repoName: String) {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getRepoDetailUseCase(repoName)) {
            is Result.Success -> _uiState.update { it.copy(isLoading = false, repo = result.data) }

            is Result.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.errorMessage)
                }
            }
        }
    }

    companion object {
        private const val REPO_NAME_SAVED_STATE_KEY = "repo_name"
    }
}
