package com.sebas.platzichallenge.domain.model

data class DetailMovie(
    val backdropPath: String = "",
    val title: String = "",
    val tagline: String = "",
    val overview: String = "",
    val genres: List<Genre> = emptyList(),
    val releaseDate: String = "",
    val runtime: Int = 0,
    val voteAverage: Float = 0.0f,
    val companies: List<Companies> = emptyList()
)

data class Genre(val id: Int, val name: String)

data class Companies(val name: String, val image: String)