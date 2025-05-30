package com.sebas.platzichallenge.data.repositoryimpl

import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.data.common.ErrorMessages.DELETE_FAVORITE_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.SAVE_FAVORITE_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.UPDATE_FAVORITE_ERROR
import com.sebas.platzichallenge.data.datasource.FavoriteMoviesDataSource
import com.sebas.platzichallenge.data.mapper.toDomain
import com.sebas.platzichallenge.data.mapper.toEntity
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import com.sebas.platzichallenge.domain.repository.FavoriteMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
    private val dataSource: FavoriteMoviesDataSource
): FavoriteMoviesRepository {


    override fun fetchFavoriteMovies(): Flow<Result<List<FavoriteMovie>>> = flow {
        emit(Result.Loading)
        val listFavoritesMovies = dataSource.fetchFavoriteMovies().map { it.toDomain() }
        emit(Result.Success(listFavoritesMovies))
    }.flowOn(Dispatchers.IO)

    override suspend fun saveFavoriteMovie(
        favoriteMovie: FavoriteMovie
    ): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        dataSource.saveFavoriteMovie(favoriteMovie.toEntity())
        emit(Result.Success(Unit))
    }.catch {
        emit(Result.Error(SAVE_FAVORITE_ERROR))
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        dataSource.deleteFavoriteMovie(favoriteMovie.toEntity())
        emit(Result.Success(Unit))
    }.catch {
        emit(Result.Error(DELETE_FAVORITE_ERROR))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateFavoriteMovie(favoriteMovie: FavoriteMovie): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        dataSource.updateFavoriteMovie(favoriteMovie.toEntity())
        emit(Result.Success(Unit))
    }.catch {
        emit(Result.Error(UPDATE_FAVORITE_ERROR))
    }.flowOn(Dispatchers.IO)
}