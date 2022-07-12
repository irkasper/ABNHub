package com.abnamro.abnhub.domain.screens.repo_detail

import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.domain.repository.RepoRepository
import com.abnamro.abnhub.utils.Result
import javax.inject.Inject

class GetRepoDetailUseCase @Inject constructor(
    private val repoRepository: RepoRepository,
) {
    suspend operator fun invoke(repoName: String): Result<RepoDetail> {
        return repoRepository.getRepoDetail(repoName)
    }
}
