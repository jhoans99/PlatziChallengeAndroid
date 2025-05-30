package com.sebas.platzichallenge.data.mapper

import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity
import com.sebas.platzichallenge.domain.model.FavoriteMovie

fun FavoriteMovie.toEntity() = FavoriteMovieEntity(
    id = id,
    title = title,
    posterPath = posterImage,
    description = description,
    category = category
)

fun FavoriteMovieEntity.toDomain() = FavoriteMovie(
    id = id,
    title = title,
    posterImage = posterPath,
    description = description,
    category = category
)