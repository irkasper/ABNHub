package com.abnamro.abnhub.di

import android.content.Context
import com.abnamro.abnhub.data.local_data_source.RepoDao
import com.abnamro.abnhub.data.model.RepoDto
import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.data.remote_data_source.GitHubService
import com.abnamro.abnhub.data.repository.RepoRepositoryImpl
import com.abnamro.abnhub.data.util.RepoDetailDomainMapper
import com.abnamro.abnhub.data.util.RepoDomainMapper
import com.abnamro.abnhub.data.util.RepoModelMapper
import com.abnamro.abnhub.data.util.networkobserver.NetworkObserver
import com.abnamro.abnhub.data.util.networkobserver.NetworkObserverImpl
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.domain.repository.RepoRepository
import com.abnamro.abnhub.domain.util.Mapper
import com.abnamro.abnhub.utils.ResourcesProvider
import com.abnamro.abnhub.utils.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DI Module for application scope
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepoRepository(
        resourcesProvider: ResourcesProvider,
        gitHubService: GitHubService,
        repoDomainMapper: Mapper<RepoModel, Repo>,
        repoDetailDomainMapper: Mapper<RepoModel, RepoDetail>,
        repoDao: RepoDao,
        repoModelMapper: Mapper<RepoDto, RepoModel>,
    ): RepoRepository = RepoRepositoryImpl(
        resourcesProvider = resourcesProvider,
        gitHubService = gitHubService,
        repoDao = repoDao,
        repoDomainMapper = repoDomainMapper,
        repoDetailDomainMapper = repoDetailDomainMapper,
        repoModelMapper = repoModelMapper,
    )

    @Singleton
    @Provides
    fun provideRepoDomainMapperImpl(): Mapper<RepoModel, Repo> =
        RepoDomainMapper()

    @Singleton
    @Provides
    fun provideRepoModelMapper(): Mapper<RepoDto, RepoModel> =
        RepoModelMapper()

    @Singleton
    @Provides
    fun provideRepoDetailDomainMapper(): Mapper<RepoModel, RepoDetail> =
        RepoDetailDomainMapper()

    @Singleton
    @Provides
    fun provideResourcesProvider(
        @ApplicationContext context: Context
    ): ResourcesProvider = ResourcesProviderImpl(context)

    @Singleton
    @Provides
    fun provideNetworkObserver(
        @ApplicationContext context: Context
    ): NetworkObserver = NetworkObserverImpl(context)
}
