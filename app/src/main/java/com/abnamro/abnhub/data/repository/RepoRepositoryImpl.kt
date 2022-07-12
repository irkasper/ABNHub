package com.abnamro.abnhub.data.repository

import com.abnamro.abnhub.R
import com.abnamro.abnhub.data.local_data_source.RepoDao
import com.abnamro.abnhub.data.model.RepoDto
import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.data.remote_data_source.GitHubService
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.domain.repository.RepoRepository
import com.abnamro.abnhub.domain.util.Mapper
import com.abnamro.abnhub.utils.PAGE_SIZE
import com.abnamro.abnhub.utils.ResourcesProvider
import com.abnamro.abnhub.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Based on SINGLE-SOURCE-OF-TRUTH strategy
 * it will try to read data from API
 * in case of exceptions (like 403, no internet, etc)
 * then we will try to read cached data from database
 */
class RepoRepositoryImpl(
    private val resourcesProvider: ResourcesProvider,
    private val gitHubService: GitHubService,
    private val repoDao: RepoDao,
    private val repoDomainMapper: Mapper<RepoModel, Repo>,
    private val repoDetailDomainMapper: Mapper<RepoModel, RepoDetail>,
    private val repoModelMapper: Mapper<RepoDto, RepoModel>,
) : RepoRepository {

    override fun observeRepos(): Flow<List<Repo>> {
        return repoDao.observeRepos()
            .map { repoDomainMapper.mapList(it) }
    }

    override suspend fun updateData(page: Int): Result<Int> {
        val result = try {
            val repos = gitHubService.fetchRepos(page, PAGE_SIZE)
            if (page == 1) {
                repoDao.deleteAll()
            }
            repoDao.insertRepos(repoModelMapper.mapList(repos))
            Result.Success(repos.size)
        } catch (e: Exception) {
            Result.Error(handleAPIErrors(e))
        }

        return result
    }

    override suspend fun getRepoDetail(repoName: String): Result<RepoDetail> {
        val result = try {
            val repoDto = gitHubService.getRepoDetail(repoName)
            Result.Success(repoDetailDomainMapper.map(repoModelMapper.map(repoDto)))
        } catch (e: Exception) {
            val repo = repoDao.getRepo(repoName)
            if (repo != null)
                Result.Success(repoDetailDomainMapper.map(repo))
            else
                Result.Error(handleAPIErrors(e))
        }

        return result
    }

    private fun handleAPIErrors(e: Exception) =
        when (e) {
            is UnknownHostException -> resourcesProvider.getString(R.string.api_errors_no_internet)
            is HttpException ->
                when (e.code()) {
                    403 -> resourcesProvider.getString(R.string.api_errors_http403)
                    else -> e.response()?.errorBody()?.string().orEmpty()
                }
            else -> resourcesProvider.getString(R.string.api_errors_undefined, e.message)
        }
}
