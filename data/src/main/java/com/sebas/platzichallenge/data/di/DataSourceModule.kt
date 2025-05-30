package com.sebas.platzichallenge.data.di

import com.sebas.platzichallenge.data.datasource.FavoriteMoviesDataSource
import com.sebas.platzichallenge.data.datasource.MoviesDataSource
import com.sebas.platzichallenge.data.datasource.implementation.FavoriteMoviesDataSourceImpl
import com.sebas.platzichallenge.data.datasource.implementation.MoviesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteMoviesDataSource(
        impl: FavoriteMoviesDataSourceImpl
    ): FavoriteMoviesDataSource

    @Binds
    @Singleton
    abstract fun bindMoviesDataSource(
        impl: MoviesDataSourceImpl
    ): MoviesDataSource
}