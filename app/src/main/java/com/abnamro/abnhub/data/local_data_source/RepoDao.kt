package com.abnamro.abnhub.data.local_data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abnamro.abnhub.data.model.RepoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(models: List<RepoModel>): List<Long>?

    @Query("SELECT * FROM tblRepos")
    fun observeRepos(): Flow<List<RepoModel>>

    @Query("SELECT * FROM tblRepos WHERE name like :repoName")
    suspend fun getRepo(repoName: String): RepoModel?

    @Query("DELETE FROM tblRepos")
    suspend fun deleteAll()
}
