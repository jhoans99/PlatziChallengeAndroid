package com.sebas.platzichallenge.data.datasource

import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import com.sebas.platzichallenge.data.model.response.MovieReviewResponse

interface MoviesDataSource {
    suspend fun fetchMovieDetails(movieId: String): MovieDetailResponse

    suspend fun fetchMovieReviewById(movieId: String): MovieReviewResponse
}