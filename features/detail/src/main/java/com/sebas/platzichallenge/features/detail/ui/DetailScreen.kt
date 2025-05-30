package com.sebas.platzichallenge.features.detail.ui

import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.request.ImageRequest
import com.sebas.platzichallenge.core.ui.components.DialogComponent
import com.sebas.platzichallenge.core.ui.components.Loader
import com.sebas.platzichallenge.core.ui.components.PrimaryButton
import com.sebas.platzichallenge.core.ui.theme.Green
import com.sebas.platzichallenge.core.ui.theme.GreenDark
import com.sebas.platzichallenge.core.ui.theme.LightGray
import com.sebas.platzichallenge.core.ui.theme.OnPrimary
import com.sebas.platzichallenge.domain.model.Companies
import com.sebas.platzichallenge.domain.model.MovieReviews
import com.sebas.platzichallenge.features.detail.state.DetailMovieUiState
import com.sebas.platzichallenge.features.detail.utils.Constants.URL_VIDEO
import java.util.Locale

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigationBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onInitLoadData()
    }

    when {
        uiState.isLoading -> Loader()

        uiState.isShowModalError -> {
            DialogComponent(
                stringResource(
                    com.sebas.platzichallenge.core.R.string.title_modal_error
                ),
                stringResource(
                    com.sebas.platzichallenge.features.detail.R.string.message_error_get_movie_details
                ),
                isCancelAction = false,
                onDismissAction = {
                    viewModel.onUpdateShowModalError(false)
                },
                onConfirmAction = {
                    viewModel.onUpdateShowModalError(false)
                    onNavigationBack()
                }
            )
        }
    }

    DetailMovieScreen(uiState)

}

@Composable
fun DetailMovieScreen(uiState: DetailMovieUiState) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        DetailMovieBody(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp),
            uiState
        )
    }
}

@Composable
fun DetailMovieBody(
    modifier: Modifier,
    uiState: DetailMovieUiState
) {
    Column(modifier = modifier.fillMaxSize()) {
        MovieDetailScreen(
            Modifier,
            uiState
        )
    }

}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    uiState: DetailMovieUiState,
    viewModel: DetailViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        AsyncImage(
            model = uiState.detailMovie?.backdropPath ?: "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = uiState.detailMovie?.title ?: "",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (uiState.detailMovie?.tagline?.isNotEmpty() == true) {
            Text(
                text = uiState.detailMovie.tagline,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            uiState.detailMovie?.genres?.forEach { genre ->
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = OnPrimary,
                    modifier = Modifier
                        .background(
                            color = Green,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(5.dp)
                )
            }
        }
        Text(
            text = uiState.detailMovie?.overview ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )

        var showTrailer by remember {
            mutableStateOf(false)
        }

        PrimaryButton(
            Modifier.padding(top = 15.dp),
            stringResource(
                com.sebas.platzichallenge.features.detail.R.string.text_button_show_trailer
            ),
            isEnable = !showTrailer,
            onClickAction = {
                showTrailer = true
            }
        )

        when {
            showTrailer -> {
                val player = viewModel.preparePlayer(URL_VIDEO)
                SimpleVideoPlayer(
                    player,
                    modifier = Modifier.padding(vertical = 10.dp).height(200.dp),
                    onVideoEnded = {
                        showTrailer = false
                    }
                )
            }
        }

        LayoutGeneralInformation(
            Modifier,
            uiState.detailMovie?.releaseDate ?: "",
            uiState.detailMovie?.runtime.toString(),
            uiState.detailMovie?.voteAverage ?: 0.0f
        )

        ProducersCompanies(
            Modifier.fillMaxWidth(),
            uiState.detailMovie?.companies ?: emptyList()
        )

        ReviewListLayout(uiState.movieReviews ?: emptyList())
    }
}

@Composable
fun ReviewListLayout(reviews: List<MovieReviews>) {
    Text(
        text = stringResource(
            com.sebas.platzichallenge.features.detail.R.string.text_label_reviews
        ),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 400.dp)
            .padding(8.dp)
    ) {
        items(reviews) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = OnPrimary
                )
            ) {
                var expanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .animateContentSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.author,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = it.createdDate,
                        style = MaterialTheme.typography.labelSmall
                    )

                    if(!expanded) {
                        Text(
                            text = it.content,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = it.content,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = GreenDark
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LayoutGeneralInformation(
    modifier: Modifier,
    releaseDate: String,
    duration: String,
    voteAverage: Float
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, bottom = 10.dp),
    ) {
        Text(
            stringResource(
                com.sebas.platzichallenge.features.detail.R.string.text_release,
                releaseDate
            ),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            stringResource(
                com.sebas.platzichallenge.features.detail.R.string.text_duration,
                duration
            ),
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            String.format(locale = Locale.getDefault(),"%.1f ⭐", voteAverage),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ProducersCompanies(
    modifier: Modifier,
    companies: List<Companies>
) {
    val pagerState = rememberPagerState { companies.size }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                com.sebas.platzichallenge.features.detail.R.string.text_produces_companies
            ),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp)
        ) { page ->
            val company = companies[page]
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(company.image)
                        .crossfade(true)
                        .placeholder(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                        .error(com.sebas.platzichallenge.core.R.drawable.img_movie_placeholder)
                        .build(),
                    contentDescription = company.name,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = company.name,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            repeat(companies.size) {
                val color = if(pagerState.currentPage == it) Green else LightGray
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(8.dp)
                        .background(color)
                )
            }
        }
    }
}


@Composable
fun SimpleVideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    onVideoEnded: () -> Unit = {}
) {
    val context = LocalContext.current

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnded()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(16.dp))
        )

        IconButton(
            onClick = {
                exoPlayer.stop()
                onVideoEnded()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}