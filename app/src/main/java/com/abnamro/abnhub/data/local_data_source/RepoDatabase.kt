package com.abnamro.abnhub.data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abnamro.abnhub.data.model.RepoModel

@Database(
    entities = [RepoModel::class],
    version = 1,
    exportSchema = false,
)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun getRepoDao(): RepoDao

    companion object {
        const val DATABASE_NAME = "repo_database"
    }
}
