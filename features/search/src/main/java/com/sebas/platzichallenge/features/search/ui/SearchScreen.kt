@file:OptIn(ExperimentalMaterial3Api::class)

package com.sebas.platzichallenge.features.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sebas.platzichallenge.core.ui.components.Loader
import com.sebas.platzichallenge.domain.model.MovieItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sebas.platzichallenge.core.ui.components.SearchTopBar
import com.sebas.platzichallenge.core.ui.theme.GreenDark
import com.sebas.platzichallenge.core.ui.theme.OnPrimary
import com.sebas.platzichallenge.domain.model.FavoriteMovie
import com.sebas.platzichallenge.features.search.state.SearchMovieUiState
import com.sebas.platzichallenge.features.search.ui.components.DialogSaveFavorite

@Composable
fun SearchMovieRoute(
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit
) {
    val movies = viewModel.pagedMovies.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllFavoriteMovies()
    }

    when {
        movies.loadState.refresh is LoadState.Loading && movies.itemCount == 0 -> Loader()



        uiState.isShowModalCategory -> {
            DialogSaveFavorite(
                uiState.isUpdateFavorite,
                uiState.pendingFavorite.category,
                onDismiss = {
                    viewModel.onUpdateShowModalCategory(false)
                },
                onSaveMovie = {
                    viewModel.onUpdateShowModalCategory(false)
                    when {
                        uiState.isUpdateFavorite -> {
                            viewModel.isUpdateFavoriteMovie(false, FavoriteMovie())
                            viewModel.updateFavoriteMovie(
                                uiState.updatedFavorite.apply {
                                    this.category = it
                                }
                            )
                        }
                        else -> {
                            viewModel.saveFavorite(
                                uiState.pendingFavorite.apply {
                                    this.category = it
                                }
                            )
                        }
                    }
                }
            )
        }
    }

    SearchMovieScreen(movies,uiState) {
        onNavigateDetail(it)
    }
}

@Composable
fun SearchMovieScreen(
    movies: LazyPagingItems<MovieItem>,
    uiState: SearchMovieUiState,
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onUpdateShowSnackBar(null)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                query = uiState.query,
                onQueryChanged = {
                    viewModel.onQueryChanged(it)
                },
                onSearch = {
                    viewModel.onQueryChanged(uiState.query)
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        SearchMovieBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            movies,
            uiState
        ) {
            onNavigateDetail(it)
        }
    }
}

@Composable
fun SearchMovieBody(
    modifier: Modifier,
    movies: LazyPagingItems<MovieItem>,
    uiState: SearchMovieUiState,
    onNavigateDetail: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        AnimatedVisibility(
            uiState.listFavoriteItem.isNotEmpty(),
            modifier = Modifier
        ) {
            Column {
                Text(
                    text = stringResource(
                        com.sebas.platzichallenge.features.search.R.string.label_favorite_movies
                    ),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
                FavoriteMovieList(Modifier, uiState.listFavoriteItem) {
                    onNavigateDetail(it)
                }
            }
        }

        Text(
            text = stringResource(
                com.sebas.platzichallenge.features.search.R.string.label_trending_movies
            ),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 5.dp)
        )

        MovieList(Modifier.padding(top =  20.dp),movies,uiState) {
            onNavigateDetail(it)
        }

    }
}

@Composable
fun MovieList(
    modifier: Modifier,
    movies: LazyPagingItems<MovieItem>,
    uiState: SearchMovieUiState,
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit
) {
    val favorite = uiState.listFavoriteItem.map { it.id.toString() }.toSet()

    when {
        movies.loadState.hasError -> {
            RetryAgain(modifier) {
                movies.retry()
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxWidth()
            ) {
                items(movies.itemCount) {
                    movies[it]?.let {
                        val isFavorite = it.id.toString() in favorite
                        MovieListItem(
                            it,
                            isFavorite = isFavorite,
                            onToggleFavorite = { movie ->
                                viewModel.toggleFavorite(movie,isFavorite)
                            },
                            onSelectedMovie = { idMovie ->
                                onNavigateDetail(idMovie)
                            }
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun MovieListItem(
    movieItem: MovieItem,
    isFavorite: Boolean,
    onToggleFavorite: (MovieItem) -> Unit,
    onSelectedMovie: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onSelectedMovie(movieItem.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = OnPrimary
        )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieItem.poster)
                    .crossfade(true)
                    .placeholder(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                    .error(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                    .build(),
                contentDescription = movieItem.titleMovie,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds,

            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movieItem.titleMovie,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        onToggleFavorite(movieItem)
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }


                Text(
                    movieItem.releaseDate,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = movieItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                ItemRatingLayout(
                    Modifier.padding(top = 8.dp),
                    movieItem.rating
                )
            }
        }
    }
}

@Composable
fun ItemRatingLayout(
    modifier: Modifier,
    rating: Float
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.Yellow,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${String.format("%.1f", rating)}/10" ,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun FavoriteMovieList(
    modifier: Modifier,
    listFavorite: List<FavoriteMovie>,
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onNavigateDetail: (String) -> Unit
) {
    LazyRow (
        modifier.fillMaxWidth()
    ) {
        items(
            listFavorite,
            key = { it.id }
        ) { movieFavorite ->
            FavoriteMovieItem(
                movieFavorite,
                onEditCategory = {
                    viewModel.onUpdateShowModalCategory(true)
                    viewModel.isUpdateFavoriteMovie(true,it)
                },
                onNavigateDetail = {
                    onNavigateDetail(it)
                }
            )
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: FavoriteMovie,
    onEditCategory: (FavoriteMovie) -> Unit,
    onNavigateDetail: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
            .height(240.dp)
            .clickable {
                onNavigateDetail(movie.id.toString())
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = OnPrimary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .padding(top = 5.dp)
                .clip(RoundedCornerShape(8.dp))) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.posterImage)
                        .crossfade(true)
                        .placeholder(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                        .error(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                        .build(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onEditCategory(movie) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 5.dp, vertical = 8.dp)
                        .size(24.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
                ,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


            Text(
                text = movie.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun RetryAgain(
    modifier: Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Ha ocurrido un error al obtener la información",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                IconButton(
                    onClick = {
                        onRetry()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = GreenDark,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}


