package com.sebas.platzichallenge.data.mapper

import com.sebas.platzichallenge.core.util.formatDate
import com.sebas.platzichallenge.data.model.response.MovieItemResponse
import com.sebas.platzichallenge.domain.model.MovieItem

fun MovieItemResponse.toDomain() = MovieItem(
    titleMovie = title,
    description = overview,
    poster = "https://image.tmdb.org/t/p/w342/${posterPath}",
    rating = voteAverage,
    releaseDate = if(releaseDate.isNotEmpty()) releaseDate.formatDate() else "",
    id = id.toString(),
    isFavorite = false
)