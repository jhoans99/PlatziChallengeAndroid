package com.sebas.platzichallenge.data.datasource.remote

import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import com.sebas.platzichallenge.data.model.response.MovieResponse
import com.sebas.platzichallenge.data.model.response.MovieReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id")
        id: String
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieId: String
    ): Response<MovieReviewResponse>
}