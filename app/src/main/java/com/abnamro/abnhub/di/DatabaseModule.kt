package com.abnamro.abnhub.di

import android.content.Context
import androidx.room.Room
import com.abnamro.abnhub.data.local_data_source.RepoDao
import com.abnamro.abnhub.data.local_data_source.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RepoDatabase =
        Room.databaseBuilder(
            context,
            RepoDatabase::class.java,
            RepoDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideRepoDao(database: RepoDatabase): RepoDao = database.getRepoDao()
}
