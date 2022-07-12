package com.abnamro.abnhub.ui.screens.repo_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.abnamro.abnhub.R
import com.abnamro.abnhub.ui.screens.repo_detail.component.HAppBar
import com.abnamro.abnhub.ui.screens.repo_detail.component.RepoDetail

@Composable
internal fun RepoDetailScreen(
    viewModel: RepoDetailViewModel,
    navigateUp: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    RepoDetailScreen(uiState = uiState, navigateUp = navigateUp)
}

@Composable
internal fun RepoDetailScreen(uiState: RepoDetailUiState, navigateUp: () -> Unit) {
    val screenData: Pair<String, @Composable () -> Unit> = when {

        uiState.isLoading -> stringResource(R.string.loading) to { CircularProgressIndicator() }

        uiState.errorMessage == null && uiState.repo != null ->
            uiState.repo.name to { RepoDetail(repo = uiState.repo) }

        else -> stringResource(R.string.error) to { Text(text = uiState.errorMessage ?: "") }
    }

    Scaffold(
        topBar = { HAppBar(screenData.first, navigateUp) },
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            screenData.second()
        }
    }
}
