package com.sebas.platzichallenge.data.mapper

import com.sebas.platzichallenge.core.util.formatDate
import com.sebas.platzichallenge.data.model.response.MovieReviewItem
import com.sebas.platzichallenge.domain.model.MovieReviews

fun MovieReviewItem.toDomain() = MovieReviews(
    author = author,
    content = content,
    createdDate = createDte.formatDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    rating = authorDetails.rating,
    id = id
)