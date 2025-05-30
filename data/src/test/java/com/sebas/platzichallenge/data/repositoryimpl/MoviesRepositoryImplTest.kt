package com.sebas.platzichallenge.data.repositoryimpl

import com.sebas.platzichallenge.data.datasource.MoviesDataSource
import com.sebas.platzichallenge.data.datasource.remote.MovieApiService
import com.sebas.platzichallenge.data.mapper.toDomain
import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import com.sebas.platzichallenge.domain.model.DetailMovie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.data.common.ErrorMessages.MOVIE_DETAILS_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.MOVIE_REVIEWS_ERROR
import com.sebas.platzichallenge.data.model.response.MovieReviewResponse
import com.sebas.platzichallenge.domain.model.MovieReviews
import io.mockk.every

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @MockK
    private lateinit var apiService: MovieApiService

    @MockK
    private lateinit var moviesDataSource: MoviesDataSource

    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        repository = MoviesRepositoryImpl(apiService,moviesDataSource)

    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun fetchMovieDetailsReturnsLoadingAndSuccessWhenDataSourceReturnData() {
        scope.runTest {
            val mockkResponse = MovieDetailResponse(releaseDate = "2025-01-01")
            val mockkResponseDomain = mockkResponse.toDomain()
            coEvery { moviesDataSource.fetchMovieDetails(any()) } returns mockkResponse

            val response = repository.fetchMovieDetails("1").toList()

            assert(response.first() is Result.Loading)
            assert(response[1] is Result.Success)
            assertEquals(mockkResponseDomain, (response[1] as Result.Success).data)
        }
    }

    @Test
    fun fetchMovieDetailsReturnsLoadingAndErrorWhenDataSourceReturnException() {
        scope.runTest {
            coEvery { moviesDataSource.fetchMovieDetails(any()) } throws RuntimeException("Error Api")

            val response = repository.fetchMovieDetails("1").toList()

            assert(response.first() is Result.Loading)
            assert(response[1] is Result.Error)
            assertEquals(MOVIE_DETAILS_ERROR, (response[1] as Result.Error).error)
        }
    }

    @Test
    fun fetchMovieReviewsReturnsLoadingAndSuccessWhenDataSourceReturnData() {
        scope.runTest {
            val mockkResponse = MovieReviewResponse()
            val mockkResponseDomain = mockkResponse.results.map { it.toDomain() }
            coEvery { moviesDataSource.fetchMovieReviewById(any()) } returns mockkResponse

            val response = repository.fetchMovieReviews("1").toList()

            assert(response.first() is Result.Loading)
            assert(response[1] is Result.Success)
            assertEquals(mockkResponseDomain, (response[1] as Result.Success).data)
        }
    }

    @Test
    fun fetchMovieReviewReturnsLoadingAndErrorWhenDataSourceReturnException() {
        scope.runTest {
            coEvery { moviesDataSource.fetchMovieReviewById(any()) } throws RuntimeException("Error Api")

            val response = repository.fetchMovieReviews("1").toList()

            assert(response.first() is Result.Loading)
            assert(response[1] is Result.Error)
            assertEquals(MOVIE_REVIEWS_ERROR, (response[1] as Result.Error).error)
        }
    }

}