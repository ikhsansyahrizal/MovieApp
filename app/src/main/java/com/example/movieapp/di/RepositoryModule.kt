package com.example.movieapp.di

import com.example.movieapp.domain.repository.RemoteRepository
import com.example.movieapp.domain.repository.RemoteRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteRepositoryImp: RemoteRepositoryImp): RemoteRepository

}