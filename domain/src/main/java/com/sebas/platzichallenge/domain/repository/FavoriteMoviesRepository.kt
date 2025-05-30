package com.sebas.platzichallenge.domain.repository

import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesRepository {
    fun fetchFavoriteMovies(): Flow<Result<List<FavoriteMovie>>>

    suspend fun saveFavoriteMovie(
        favoriteMovie: FavoriteMovie
    ): Flow<Result<Unit>>

    suspend fun deleteFavoriteMovie(
        favoriteMovie: FavoriteMovie
    ): Flow<Result<Unit>>

    suspend fun updateFavoriteMovie(
        favoriteMovie: FavoriteMovie
    ): Flow<Result<Unit>>
}