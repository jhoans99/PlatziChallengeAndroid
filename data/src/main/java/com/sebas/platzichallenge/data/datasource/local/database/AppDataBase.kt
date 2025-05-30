package com.sebas.platzichallenge.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sebas.platzichallenge.data.datasource.local.dao.FavoriteMovieDao
import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao

}