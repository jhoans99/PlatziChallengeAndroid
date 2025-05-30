package com.sebas.platzichallenge.data.datasource.implementation

import com.sebas.platzichallenge.data.datasource.remote.MovieApiService
import com.sebas.platzichallenge.data.model.response.MovieDetailResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesDataSourceImplTest {
    val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @MockK
    private lateinit var apiService: MovieApiService

    private lateinit var dataSource: MoviesDataSourceImpl

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        dataSource = MoviesDataSourceImpl(apiService)
    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun fetchMovieDetailsReturnsDataWhenResponseIsSuccessful() {
        scope.runTest {
            val responseDto: MovieDetailResponse = mockk()

            coEvery {
                apiService.getDetailMovie("")
            } returns Response.success(responseDto)

            val result = dataSource.fetchMovieDetails("")

            assertEquals(responseDto,result)

            coVerify(exactly = 1) {
                apiService.getDetailMovie("")
            }
        }
    }

    @Test(expected = Exception::class)
    fun fetchMovieReviewByIdThrowsExceptionWhenResponseIsNotSuccessful() {
        scope.runTest {
            coEvery {
                apiService.getMovieReviews(any())
            } returns Response.error(401, "Authorized".toResponseBody())

            dataSource.fetchMovieReviewById("1")

            coVerify(exactly = 1) {
                apiService.getMovieReviews("1")
            }
        }
    }

}