package com.abnamro.abnhub.domain.repository

import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.utils.Result
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun observeRepos(): Flow<List<Repo>>
    suspend fun updateData(page: Int): Result<Int>
    suspend fun getRepoDetail(repoName: String): Result<RepoDetail>
}
