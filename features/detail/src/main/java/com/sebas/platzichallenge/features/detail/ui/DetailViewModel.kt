package com.sebas.platzichallenge.features.detail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import com.sebas.platzichallenge.features.detail.state.DetailMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
    private val exoPlayer: ExoPlayer
): ViewModel() {

    private val idMovie = savedStateHandle.get<String>("id") ?: ""

    private var _uiState = MutableStateFlow(DetailMovieUiState())
    val uiState: StateFlow<DetailMovieUiState> = _uiState

    private var currentUrl: String? = null

    fun onInitLoadData() {
        viewModelScope.launch {
            combine(
                moviesRepository.fetchMovieDetails(idMovie),
                moviesRepository.fetchMovieReviews(idMovie)
            ) { resultMovieDetails, resultMovieReviews ->

                val movieDetails = if(resultMovieDetails is Result.Success) {
                    resultMovieDetails.data
                }  else  null

                val movieReviews = if(resultMovieReviews is Result.Success) {
                    resultMovieReviews.data
                } else null

                if(resultMovieDetails is Result.Error) {
                    onUpdateShowModalError(true)
                }

                _uiState.update {
                    it.copy(
                        detailMovie = movieDetails,
                        movieReviews = movieReviews,
                        isLoading = resultMovieDetails is Result.Loading || resultMovieReviews is Result.Loading
                    )
                }
            }.catch {
                onUpdateShowModalError(true)
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(20000L),
                initialValue = DetailMovieUiState(isLoading = true)
            ).collect {
                _uiState.update { it }
            }
        }
    }


    fun preparePlayer(videoUrl: String): ExoPlayer {
        currentUrl = videoUrl
        return exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = false
        }
    }

    fun onUpdateShowModalError(value: Boolean) {
        _uiState.update { it.copy(isShowModalError = value) }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}