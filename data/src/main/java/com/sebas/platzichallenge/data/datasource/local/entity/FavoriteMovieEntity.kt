package com.sebas.platzichallenge.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("posterPath")
    val posterPath: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("category")
    val category: String
)
