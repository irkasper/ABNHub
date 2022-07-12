package com.abnamro.abnhub.di

import com.abnamro.abnhub.data.remote_data_source.GitHubService
import com.abnamro.abnhub.utils.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            // for logging received data
            .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
            .build()

    @Singleton
    @Provides
    fun provideGitHubService(
        okHttpClient: OkHttpClient,
    ): GitHubService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GitHubService::class.java)
}
