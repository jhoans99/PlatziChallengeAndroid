package com.sebas.platzichallenge.features.detail.state

import androidx.media3.exoplayer.ExoPlayer
import com.sebas.platzichallenge.domain.model.DetailMovie
import com.sebas.platzichallenge.domain.model.MovieReviews

data class DetailMovieUiState(
    val isLoading: Boolean = false,
    val detailMovie: DetailMovie? = DetailMovie(),
    val movieReviews: List<MovieReviews>? = emptyList(),
    var playState: ExoPlayer? = null,
    val isShowModalError: Boolean = false
)
