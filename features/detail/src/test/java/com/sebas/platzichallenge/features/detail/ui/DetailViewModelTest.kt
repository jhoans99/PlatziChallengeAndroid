package com.sebas.platzichallenge.features.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.media3.exoplayer.ExoPlayer
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.model.DetailMovie
import com.sebas.platzichallenge.domain.model.MovieReviews
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import com.sebas.platzichallenge.features.detail.state.DetailMovieUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    val dispatcher = StandardTestDispatcher()
    val scope = TestScope(dispatcher)

    @MockK
    private val saveStateHandle = mockk<SavedStateHandle>()

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @MockK
    private lateinit var exoPlayer: ExoPlayer

    private lateinit var viewModel: DetailViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        coEvery { saveStateHandle.get<String>("id") } returns "5050"

        viewModel = DetailViewModel(saveStateHandle,moviesRepository,exoPlayer)
    }

    @After
    fun after() {
        unmockkAll()
        Dispatchers.resetMain()
    }


    @Test
    fun onInitLoadDataEmitsSuccessStateWhenBothFlowsSucceed() {
        scope.runTest {
            val detail: DetailMovie = mockk()
            val reviews = listOf(MovieReviews(id="r1", author="A", content = "T", createdDate = "", rating = 0.0f))

            coEvery { moviesRepository.fetchMovieDetails(any()) } returns flowOf(
                Result.Loading,
                Result.Success(detail)
            )
            coEvery { moviesRepository.fetchMovieReviews(any()) } returns flowOf(
                Result.Loading,
                Result.Success(reviews)
            )

            val states = mutableListOf<DetailMovieUiState>()
            val job = launch {
                viewModel.uiState.toList(states)
            }

            viewModel.onInitLoadData()
            runCurrent()

            val final = states.last()
            assertEquals(detail, final.detailMovie)
            assertEquals(reviews, final.movieReviews)
            assertFalse(final.isLoading)
            assertFalse(final.isShowModalError)

            coVerify(exactly = 1) { moviesRepository.fetchMovieDetails(any()) }
            coVerify(exactly = 1) { moviesRepository.fetchMovieReviews(any()) }

            job.cancelAndJoin()
        }
    }

    @Test
    fun onInitLoadDataShowsErrorWhenDetailsFlowErrors() {
        scope.runTest {
            coEvery { moviesRepository.fetchMovieDetails(any()) } returns flowOf(
                Result.Loading,
                Result.Error("fail")
            )
            coEvery { moviesRepository.fetchMovieReviews(any()) } returns flowOf(
                Result.Success(emptyList())
            )

            val states = mutableListOf<DetailMovieUiState>()
            val job = launch { viewModel.uiState.toList(states) }

            viewModel.onInitLoadData()
            runCurrent()

            val final = states.last()
            assertTrue(final.isShowModalError)
            assertNull(final.detailMovie)

            coVerify(exactly = 1) { moviesRepository.fetchMovieDetails(any()) }
            coVerify(exactly = 1) { moviesRepository.fetchMovieReviews(any()) }

            job.cancel()
        }
    }


    @Test
    fun onUpdateShowModalErrorToggleTheFlag() {
        viewModel.onUpdateShowModalError(true)
        assertTrue(viewModel.uiState.value.isShowModalError)
        viewModel.onUpdateShowModalError(false)
        assertFalse(viewModel.uiState.value.isShowModalError)
    }

}