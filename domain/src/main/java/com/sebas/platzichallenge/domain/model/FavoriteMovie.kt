package com.sebas.platzichallenge.domain.model

data class FavoriteMovie(
    val title: String = "",
    val description: String = "",
    val posterImage: String = "",
    val id: Int = 0,
    var category: String = ""
)
