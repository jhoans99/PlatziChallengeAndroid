package com.sebas.platzichallenge.data.repositoryimpl

import androidx.compose.foundation.rememberScrollState
import com.sebas.platzichallenge.data.datasource.FavoriteMoviesDataSource
import com.sebas.platzichallenge.data.mapper.toEntity
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
import com.sebas.platzichallenge.data.common.ErrorMessages.DELETE_FAVORITE_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.SAVE_FAVORITE_ERROR
import com.sebas.platzichallenge.data.common.ErrorMessages.UPDATE_FAVORITE_ERROR
import io.mockk.Runs
import io.mockk.coVerify
import io.mockk.just

@ExperimentalCoroutinesApi
class FavoriteMoviesRepositoryImplTest {
    val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @MockK
    private lateinit var dataSource: FavoriteMoviesDataSource

    private lateinit var repository: FavoriteMoviesRepositoryImpl

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        repository = FavoriteMoviesRepositoryImpl(dataSource)
    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    private val sampleDomain = FavoriteMovie(
        title = "T",
        description = "D",
        posterImage = "P",
        id = 1,
        category = "C"
    )
    private val sampleEntity = sampleDomain.toEntity()

    @Test
    fun fetchFavoriteMoviesEmitsLoadingThenSuccessWithMappedList() {
        scope.runTest {
            coEvery { dataSource.fetchFavoriteMovies() } returns listOf(sampleEntity)

            val emissions = repository.fetchFavoriteMovies().toList()

            assertTrue(emissions[0] is Result.Loading)
            assertTrue(emissions[1] is Result.Success)
            val list = (emissions[1] as Result.Success).data
            assertEquals(1, list.size)
            assertEquals(sampleDomain, list[0])
            coVerify(exactly = 1) { dataSource.fetchFavoriteMovies() }
        }
    }

    @Test
    fun saveFavoriteMovieEmitsLoadingThenSuccess() {
        scope.runTest {
            coEvery { dataSource.saveFavoriteMovie(sampleEntity) } just Runs

            val emissions = repository.saveFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            assertTrue(emissions[1] is Result.Success)
            coVerify(exactly = 1) { dataSource.saveFavoriteMovie(sampleEntity)  }
        }
    }

    @Test
    fun saveFavoriteMovieEmitsErrorOnException() {
        scope.runTest {
            coEvery { dataSource.saveFavoriteMovie(sampleEntity) } throws RuntimeException()

            val emissions = repository.saveFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            val error = emissions[1] as Result.Error
            assertEquals(SAVE_FAVORITE_ERROR, error.error)
        }
    }

    @Test
    fun deleteFavoriteMovieEmitsLoadingThenSuccess()  {
        scope.runTest {
            coEvery { dataSource.deleteFavoriteMovie(sampleEntity) } just Runs

            val emissions = repository.deleteFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            assertTrue(emissions[1] is Result.Success)
            coVerify(exactly = 1) { dataSource.deleteFavoriteMovie(sampleEntity)  }
        }
    }

    @Test
    fun deleteFavoriteMovieEmitsErrorOnException() {
        scope.runTest {
            coEvery { dataSource.deleteFavoriteMovie(sampleEntity) } throws RuntimeException()

            val emissions = repository.deleteFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            val error = emissions[1] as Result.Error
            assertEquals(DELETE_FAVORITE_ERROR, error.error)
        }
    }

    @Test
    fun updateFavoriteMovieEmitsLoadingThenSuccess() {
        scope.runTest {
            coEvery { dataSource.updateFavoriteMovie(sampleEntity) } just Runs

            val emissions = repository.updateFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            assertTrue(emissions[1] is Result.Success)
            coVerify(exactly = 1) { dataSource.updateFavoriteMovie(sampleEntity)  }
        }
    }

    @Test
    fun updateFavoriteMovieEmitsErrorOnException() {
        scope.runTest {
            coEvery { dataSource.updateFavoriteMovie(sampleEntity) } throws RuntimeException()

            val emissions = repository.updateFavoriteMovie(sampleDomain).toList()

            assertTrue(emissions[0] is Result.Loading)
            val error = emissions[1] as Result.Error
            assertEquals(UPDATE_FAVORITE_ERROR, error.error)
        }
    }


}