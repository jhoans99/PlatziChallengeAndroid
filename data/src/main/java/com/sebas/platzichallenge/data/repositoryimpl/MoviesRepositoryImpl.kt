package com.sebas.platzichallenge.data.repositoryimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.data.common.ErrorMessages.MOVIE_DETAILS_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.MOVIE_REVIEWS_ERROR
import com.sebas.platzichallenge.data.datasource.MoviesDataSource
import com.sebas.platzichallenge.data.datasource.paging.MoviePagingDataSource
import com.sebas.platzichallenge.data.datasource.remote.MovieApiService
import com.sebas.platzichallenge.data.mapper.toDomain
import com.sebas.platzichallenge.domain.model.DetailMovie
import com.sebas.platzichallenge.domain.model.MovieItem
import com.sebas.platzichallenge.domain.model.MovieReviews
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    private val moviesDataSource: MoviesDataSource
): MoviesRepository {

    override fun fetchAllMovies(query: String): Flow<PagingData<MovieItem>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 6)) {
            MoviePagingDataSource(apiService,query)
        }.flow
    }

    override fun fetchMovieDetails(movieId: String): Flow<Result<DetailMovie>> = flow {
        emit(Result.Loading)
        val result = moviesDataSource.fetchMovieDetails(movieId)
        emit(Result.Success(result.toDomain()))
    }.catch {
        emit(Result.Error(MOVIE_DETAILS_ERROR))
    }

    override fun fetchMovieReviews(movieId: String): Flow<Result<List<MovieReviews>>> = flow {
        emit(Result.Loading)
        val result = moviesDataSource.fetchMovieReviewById(movieId)
        emit(Result.Success(result.results.map { it.toDomain() }))
    }.catch {
        emit(Result.Error(MOVIE_REVIEWS_ERROR))
    }
}