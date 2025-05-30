package com.sebas.platzichallenge.data.di


import com.sebas.platzichallenge.data.repositoryimpl.FavoriteMoviesRepositoryImpl
import com.sebas.platzichallenge.data.repositoryimpl.MoviesRepositoryImpl
import com.sebas.platzichallenge.domain.repository.FavoriteMoviesRepository
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteMoviesRepository(
        favoriteMoviesRepositoryImpl: FavoriteMoviesRepositoryImpl
    ): FavoriteMoviesRepository
}