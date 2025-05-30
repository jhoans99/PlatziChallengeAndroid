package com.sebas.platzichallenge.data.datasource

import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity

interface FavoriteMoviesDataSource {
    suspend fun fetchFavoriteMovies(): List<FavoriteMovieEntity>

    suspend  fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    suspend fun updateFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)
}