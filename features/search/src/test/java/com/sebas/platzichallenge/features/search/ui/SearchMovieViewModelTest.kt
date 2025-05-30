package com.sebas.platzichallenge.features.search.ui

import androidx.paging.PagingData
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import com.sebas.platzichallenge.domain.model.MovieItem
import com.sebas.platzichallenge.domain.repository.FavoriteMoviesRepository
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchMovieViewModelTest {

    val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @MockK
    private lateinit var favoriteMoviesRepository: FavoriteMoviesRepository

    private lateinit var viewModel: SearchMovieViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)

        every { moviesRepository.fetchAllMovies(any()) } returns flowOf(PagingData.empty())
        coEvery { favoriteMoviesRepository.fetchFavoriteMovies() } returns flowOf(Result.Success(emptyList()))

        viewModel = SearchMovieViewModel(moviesRepository,favoriteMoviesRepository)
    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun whenCallOnQueryChangedThenShouldUpUiStateWithNewValue() {
        val query = "star wars"
        viewModel.onQueryChanged(query)
        Assert.assertEquals(query,viewModel.uiState.value.query)
    }

    @Test
    fun onUpdateShowModalCategoryTogglesIsShowModalCategory(){
        viewModel.onUpdateShowModalCategory(true)
        assertTrue(viewModel.uiState.value.isShowModalCategory)
        viewModel.onUpdateShowModalCategory(false)
        assertFalse(viewModel.uiState.value.isShowModalCategory)
    }

    @Test
    fun isUpdateFavoriteMovieSetsFlagsAndPendingMovie() {
        val fav = FavoriteMovie("t","d","p",1)
        viewModel.isUpdateFavoriteMovie(true, fav)
        val state = viewModel.uiState.value
        assertTrue(state.isUpdateFavorite)
    }

    @Test
    fun toggleFavoriteCallsDeleteWhenAlreadyFavorite() {
        scope.runTest {
            val movie = MovieItem(id = "2", titleMovie = "t", description = "", poster = "", rating = 0f, releaseDate = "", isFavorite = true)
            coEvery { favoriteMoviesRepository.deleteFavoriteMovie(any()) } returns flow {
                emit(Result.Success(Unit))
            }

            viewModel.toggleFavorite(movie, isFavorite = true)
            runCurrent()

            coVerify {
                favoriteMoviesRepository.deleteFavoriteMovie(any())
            }
        }
    }

    @Test
    fun saveFavoriteEmitsLoadingThenSuccessAndUpdatesSnackBarMessage() {
        scope.runTest {
            val fav = FavoriteMovie("t","d","p",1)
            coEvery { favoriteMoviesRepository.saveFavoriteMovie(fav) } returns flowOf(
                Result.Loading,
                Result.Success(Unit)
            )

            viewModel.saveFavorite(fav)
            runCurrent()

            val state = viewModel.uiState.value
            assertFalse(state.isLoading)
            assertEquals("Se agregó a favoritos", state.snackBarMessage)
        }
    }

    @Test
    fun saveFavoriteEmitsLoadingThenErrorAndUpdatesSnackBarMessage() {
        scope.runTest {
            val fav = FavoriteMovie("t","d","p",1)
            coEvery { favoriteMoviesRepository.saveFavoriteMovie(fav) } returns flowOf(
                Result.Loading,
                Result.Error("Hubo un error al agregar el favorito")
            )

            viewModel.saveFavorite(fav)
            runCurrent()

            val state = viewModel.uiState.value
            assertFalse(state.isLoading)
            assertEquals("Hubo un error al agregar el favorito", state.snackBarMessage)
        }
    }

    @Test
    fun deleteFavoriteEmitsLoadingThenErrorAndUpdatesSnackBarMessage() = runTest {
        val fav = FavoriteMovie("t","d","p",1)
        coEvery { favoriteMoviesRepository.deleteFavoriteMovie(fav) } returns flowOf(
            Result.Loading,
            Result.Error("Hubo un error al borrar el favorito")
        )

        viewModel.deleteFavorite(fav)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Hubo un error al borrar el favorito", state.snackBarMessage)
    }

    @Test
    fun deleteFavoriteEmitsLoadingThenSuccessAndUpdatesSnackBarMessage() = runTest {
        val fav = FavoriteMovie("t","d","p",1)
        coEvery { favoriteMoviesRepository.deleteFavoriteMovie(fav) } returns flowOf(
            Result.Loading,
            Result.Success(Unit)
        )

        viewModel.deleteFavorite(fav)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Se borró de favoritos", state.snackBarMessage)
    }


}