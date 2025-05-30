package com.sebas.platzichallenge.domain.model

data class MovieItem(
    val titleMovie: String,
    val description: String,
    val poster: String,
    val rating: Float,
    val releaseDate: String,
    val isFavorite: Boolean,
    val id: String
)
