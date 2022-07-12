package com.abnamro.abnhub.domain.screens.repo_list

import com.abnamro.abnhub.domain.repository.RepoRepository
import com.abnamro.abnhub.utils.Result
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(
    private val repoRepository: RepoRepository,
) {
    suspend operator fun invoke(page: Int): Result<Int> {
        return repoRepository.updateData(page)
    }
}
