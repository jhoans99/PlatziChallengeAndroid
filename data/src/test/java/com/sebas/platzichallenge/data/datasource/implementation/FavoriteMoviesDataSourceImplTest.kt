package com.sebas.platzichallenge.data.datasource.implementation

import com.sebas.platzichallenge.data.datasource.local.dao.FavoriteMovieDao
import com.sebas.platzichallenge.data.datasource.local.entity.FavoriteMovieEntity
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteMoviesDataSourceImplTest {
    val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @MockK
    private lateinit var favoriteMovieDao: FavoriteMovieDao

    private lateinit var datasource: FavoriteMoviesDataSourceImpl

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        datasource = FavoriteMoviesDataSourceImpl(favoriteMovieDao)
    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }


    @Test
    fun fetchFavoriteMoviesReturnsListFromDAO() {
        scope.runTest {
            val sampleList = listOf(
                FavoriteMovieEntity(id = 1, title = "T", description = "D", posterPath = "P", category = "C")
            )
            coEvery { favoriteMovieDao.fetchFavoriteMovies() } returns sampleList

            val result = datasource.fetchFavoriteMovies()

            assertEquals(sampleList, result)
            coVerify(exactly = 1) { favoriteMovieDao.fetchFavoriteMovies() }
        }
    }

    @Test
    fun saveFavoriteMovieCallsInsertUserOnDAO() {
        scope.runTest {
            val entity = FavoriteMovieEntity(id = 2, title = "A", description = "B", posterPath = "C", category = "X")
            coEvery { favoriteMovieDao.insertUser(entity) } just Runs

            datasource.saveFavoriteMovie(entity)

            coVerify(exactly = 1) { favoriteMovieDao.insertUser(entity) }
        }
    }

    @Test
    fun deleteFavoriteMovieCallsDeleteFavoriteOnDAO() {
        scope.runTest {
            val entity = FavoriteMovieEntity(id = 3, title = "L", description = "M", posterPath = "N", category = "Y")
            coEvery { favoriteMovieDao.deleteFavorite(entity) } just Runs

            datasource.deleteFavoriteMovie(entity)

            coVerify(exactly = 1) { favoriteMovieDao.deleteFavorite(entity) }
        }
    }

    @Test
    fun updateFavoriteMovieCallsUpdateFavoriteOnDAO() {
        scope.runTest {

            val entity = FavoriteMovieEntity(id = 4, title = "Q", description = "R", posterPath = "S", category = "Z")
            coEvery { favoriteMovieDao.updateFavorite(entity) } just Runs

            datasource.updateFavoriteMovie(entity)

            coVerify(exactly = 1) { favoriteMovieDao.updateFavorite(entity) }
        }
    }


}