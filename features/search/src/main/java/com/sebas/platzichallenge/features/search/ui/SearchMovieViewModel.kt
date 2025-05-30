@file:OptIn(FlowPreview::class)

package com.sebas.platzichallenge.features.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sebas.platzichallenge.core.model.Result
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import com.sebas.platzichallenge.domain.model.MovieItem
import com.sebas.platzichallenge.domain.repository.FavoriteMoviesRepository
import com.sebas.platzichallenge.domain.repository.MoviesRepository
import com.sebas.platzichallenge.features.search.commons.SuccessMessages.SUCCESS_DELETE_FAVORITE
import com.sebas.platzichallenge.features.search.commons.SuccessMessages.SUCCESS_SAVE_FAVORITE
import com.sebas.platzichallenge.features.search.commons.SuccessMessages.SUCCESS_UPDATE_FAVORITE
import com.sebas.platzichallenge.features.search.state.SearchMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
     moviesRepository: MoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
): ViewModel() {

    private var _uiState = MutableStateFlow(SearchMovieUiState())
    val uiState: StateFlow<SearchMovieUiState> = _uiState

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedMovies: StateFlow<PagingData<MovieItem>> = _uiState.map { it.query }
        .debounce(100)
        .flatMapLatest { query ->
            moviesRepository
                .fetchAllMovies(query)
                .cachedIn(viewModelScope)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
    }

    fun onUpdateShowModalCategory(value: Boolean) {
        _uiState.update { it.copy(isShowModalCategory = value) }
    }

    fun isUpdateFavoriteMovie(value: Boolean, favoriteMovie: FavoriteMovie) {
        _uiState.update { it.copy(isUpdateFavorite = value, updatedFavorite = favoriteMovie) }
    }


    fun toggleFavorite(movie: MovieItem,isFavorite: Boolean) {
        val favoriteMovie =
            FavoriteMovie(
                title = movie.titleMovie,
                description = movie.description,
                posterImage = movie.poster,
                id = movie.id.toInt(),
            )
        viewModelScope.launch {
            if(isFavorite) {
                deleteFavorite(favoriteMovie)
            } else {
                _uiState.update { it.copy(pendingFavorite = favoriteMovie) }
                onUpdateShowModalCategory(true)
            }

        }
    }

    fun saveFavorite(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMoviesRepository.saveFavoriteMovie(favoriteMovie).collect { result ->
                when(result) {
                    is Result.Error -> {
                        _uiState.update { it.copy(
                            isLoading = false,
                            snackBarMessage = result.error
                        ) }
                    }
                    Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false, snackBarMessage = SUCCESS_SAVE_FAVORITE) }
                        getAllFavoriteMovies()
                    }
                }
            }
        }
    }

    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie) {
        favoriteMoviesRepository.deleteFavoriteMovie(favoriteMovie).collect { result ->
            when(result) {
                is Result.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        snackBarMessage = result.error
                    ) }
                }
                Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, snackBarMessage =  SUCCESS_DELETE_FAVORITE) }
                    getAllFavoriteMovies()
                }
            }
        }
    }


    fun getAllFavoriteMovies() {
        viewModelScope.launch {
            favoriteMoviesRepository.fetchFavoriteMovies().collect { result ->
                when(result) {
                    is Result.Error -> Unit
                    Result.Loading -> Unit
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(listFavoriteItem = result.data)
                    }
                }
            }
        }
    }


    fun updateFavoriteMovie(favoriteMovie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMoviesRepository.updateFavoriteMovie(favoriteMovie).collect { result ->
                when(result) {
                    is Result.Error -> {
                        _uiState.update { it.copy(
                            isLoading = false,
                            snackBarMessage = result.error
                        ) }
                    }
                    Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(
                            isLoading = false,
                            snackBarMessage = SUCCESS_UPDATE_FAVORITE,
                            listFavoriteItem = emptyList()
                        ) }
                        getAllFavoriteMovies()
                    }
                }
            }
        }
    }

    fun onUpdateShowSnackBar(value: String?) {
        _uiState.update { it.copy(snackBarMessage = value) }
    }
}