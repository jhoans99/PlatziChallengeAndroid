package com.sebas.platzichallenge.domain.model

data class MovieReviews(
    val author: String,
    val content: String,
    val createdDate: String,
    val rating: Float?,
    val id: String
)
