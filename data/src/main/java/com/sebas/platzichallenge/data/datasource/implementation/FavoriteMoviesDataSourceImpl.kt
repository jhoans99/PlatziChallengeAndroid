package com.sebas.platzichallenge.data.datasource.implementation

import com.sebas.platzichallenge.data.datasource.FavoriteMoviesDataSource
import com.sebas.platzichallenge.data.datasource.local.dao.FavoriteMovieDao
import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity
import javax.inject.Inject

class FavoriteMoviesDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
): FavoriteMoviesDataSource {

    override suspend fun fetchFavoriteMovies(): List<FavoriteMovieEntity> {
        return favoriteMovieDao.fetchFavoriteMovies()
    }

    override suspend fun saveFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) =
        favoriteMovieDao.insertUser(favoriteMovieEntity)

    override suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) =
        favoriteMovieDao.deleteFavorite(favoriteMovieEntity)

    override suspend fun updateFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity) =
        favoriteMovieDao.updateFavorite(favoriteMovieEntity)

}