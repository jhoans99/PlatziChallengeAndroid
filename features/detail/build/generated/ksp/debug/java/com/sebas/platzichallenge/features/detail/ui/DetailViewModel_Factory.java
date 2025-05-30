package com.sebas.platzichallenge.features.detail.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.media3.exoplayer.ExoPlayer;
import com.sebas.platzichallenge.domain.repository.MoviesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DetailViewModel_Factory implements Factory<DetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<MoviesRepository> moviesRepositoryProvider;

  private final Provider<ExoPlayer> exoPlayerProvider;

  public DetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MoviesRepository> moviesRepositoryProvider, Provider<ExoPlayer> exoPlayerProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.moviesRepositoryProvider = moviesRepositoryProvider;
    this.exoPlayerProvider = exoPlayerProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), moviesRepositoryProvider.get(), exoPlayerProvider.get());
  }

  public static DetailViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<MoviesRepository> moviesRepositoryProvider, Provider<ExoPlayer> exoPlayerProvider) {
    return new DetailViewModel_Factory(savedStateHandleProvider, moviesRepositoryProvider, exoPlayerProvider);
  }

  public static DetailViewModel newInstance(SavedStateHandle savedStateHandle,
      MoviesRepository moviesRepository, ExoPlayer exoPlayer) {
    return new DetailViewModel(savedStateHandle, moviesRepository, exoPlayer);
  }
}
