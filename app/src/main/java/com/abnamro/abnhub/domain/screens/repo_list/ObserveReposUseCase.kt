package com.abnamro.abnhub.domain.screens.repo_list

import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveReposUseCase @Inject constructor(
    private val repoRepository: RepoRepository,
) {
    operator fun invoke(): Flow<List<Repo>> {
        return repoRepository.observeRepos()
    }
}
