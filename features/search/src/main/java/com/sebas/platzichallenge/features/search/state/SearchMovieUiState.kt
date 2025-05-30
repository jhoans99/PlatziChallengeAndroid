package com.sebas.platzichallenge.features.search.state

import com.sebas.platzichallenge.domain.model.FavoriteMovie

data class SearchMovieUiState(
    val isLoading: Boolean = false,
    val listFavoriteItem: List<FavoriteMovie> = emptyList(),
    val query: String = "",
    val isShowModalCategory: Boolean = false,
    val pendingFavorite: FavoriteMovie = FavoriteMovie(),
    val isUpdateFavorite: Boolean = false,
    val updatedFavorite: FavoriteMovie = FavoriteMovie(),
    val snackBarMessage: String? = null
)
