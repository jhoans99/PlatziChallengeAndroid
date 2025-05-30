package com.sebas.platzichallenge.data.datasource.implementation

import android.util.Log
import com.sebas.platzichallenge.data.common.ErrorMessages.EMPTY_DATA_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.SERVICE_ERROR
import com.sebas.platzichallenge.data.datasource.MoviesDataSource
import com.sebas.platzichallenge.data.datasource.remote.MovieApiService
import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import com.sebas.platzichallenge.data.model.response.MovieReviewResponse
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    private val moviesApiService: MovieApiService
): MoviesDataSource {

    override suspend fun fetchMovieDetails(movieId: String): MovieDetailResponse {
        val response = moviesApiService.getDetailMovie(movieId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body
            } else {
                Log.d("Error", "fetchProductByQueryApi Empty Body")
                throw Exception(EMPTY_DATA_ERROR)
            }
        } else {
            Log.d("Error", "fetchProductByQueryApi service Error ${response.code()}")
            throw Exception("$SERVICE_ERROR ${response.message()}")
        }
    }

    override suspend fun fetchMovieReviewById(movieId: String): MovieReviewResponse {
        val response = moviesApiService.getMovieReviews(movieId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body
            } else {
                Log.d("Error", "fetchProductByQueryApi Empty Body")
                throw Exception(EMPTY_DATA_ERROR)
            }
        } else {
            Log.d("Error", "fetchProductByQueryApi service Error ${response.code()}")
            throw Exception("$SERVICE_ERROR ${response.message()}")
        }
    }
}