package com.abnamro.abnhub.ui.screens.repo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abnamro.abnhub.R
import com.abnamro.abnhub.data.util.networkobserver.NetworkObserver
import com.abnamro.abnhub.domain.screens.repo_list.ObserveReposUseCase
import com.abnamro.abnhub.domain.screens.repo_list.UpdateDataUseCase
import com.abnamro.abnhub.ui.util.EspressoIdlingResource
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
class RepoListViewModel @Inject
constructor(
    private val observeReposUseCase: ObserveReposUseCase,
    private val updateDataUseCase: UpdateDataUseCase,
    private val resourcesProvider: ResourcesProvider,
    networkObserver: NetworkObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoListUiState())
    val uiState = _uiState.asStateFlow()

    internal var nextPage = 2

    init {

        networkObserver.register {
            refreshData()
        }

        viewModelScope.launch {
            observeReposUseCase().collect { repos ->
                _uiState.update { it.copy(lstRepos = repos) }
                if (repos.isNotEmpty())
                    EspressoIdlingResource.decrement()
            }
        }


    }

    fun refreshData() {
        if (!_uiState.value.isLoading) {
            _uiState.update { it.copy(errorMessage = null) }
            nextPage = 2
            viewModelScope.launch { updateData() }
        }
    }

    fun loadMoreData() {
        viewModelScope.launch { updateData() }
    }

    private suspend fun updateData() {
        if (nextPage < 0) {
            _uiState.update { it.copy(errorMessage = resourcesProvider.getString(R.string.no_more_data)) }
            return
        }

        EspressoIdlingResource.increment()
        _uiState.update { it.copy(isLoading = true) }
        when (val result = updateDataUseCase(nextPage - 1)) {
            is Result.Success -> {
                if (result.data > 0) {
                    nextPage++
                } else {
                    nextPage = -1
                    EspressoIdlingResource.decrement()
                }

                _uiState.update { it.copy(isLoading = false) }
            }
            is Result.Error -> {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.errorMessage)
                }
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
