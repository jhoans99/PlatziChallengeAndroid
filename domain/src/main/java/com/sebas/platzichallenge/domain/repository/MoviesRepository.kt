package com.sebas.platzichallenge.domain.repository

import androidx.paging.PagingData
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.model.DetailMovie
import com.sebas.platzichallenge.domain.model.MovieItem
import com.sebas.platzichallenge.domain.model.MovieReviews
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
     fun fetchAllMovies(query: String): Flow<PagingData<MovieItem>>

     fun fetchMovieDetails(movieId: String): Flow<Result<DetailMovie>>

     fun fetchMovieReviews(movieId: String): Flow<Result<List<MovieReviews>>>
}