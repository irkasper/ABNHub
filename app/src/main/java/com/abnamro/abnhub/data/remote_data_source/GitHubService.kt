package com.abnamro.abnhub.data.remote_data_source

import com.abnamro.abnhub.data.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("users/abnamrocoesd/repos")
    suspend fun fetchRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<RepoDto>

    /**
     * Supposed this API returns more data than the [fetchRepos] API.
     */
    @GET("repos/abnamrocoesd/{repoName}")
    suspend fun getRepoDetail(
        @Path("repoName") repoName: String,
    ): RepoDto
}
