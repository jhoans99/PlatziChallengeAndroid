package com.sebas.platzichallenge.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorites")
    fun fetchFavoriteMovies(): List<FavoriteMovieEntity>

    @Delete
    suspend fun deleteFavorite(favoriteMovie: FavoriteMovieEntity)

    @Update
    suspend fun updateFavorite(favoriteMovie: FavoriteMovieEntity)
}