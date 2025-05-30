package com.sebas.platzichallenge.features.search.ui;

import com.sebas.platzichallenge.domain.repository.FavoriteMoviesRepository;
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
public final class SearchMovieViewModel_Factory implements Factory<SearchMovieViewModel> {
  private final Provider<MoviesRepository> moviesRepositoryProvider;

  private final Provider<FavoriteMoviesRepository> favoriteMoviesRepositoryProvider;

  public SearchMovieViewModel_Factory(Provider<MoviesRepository> moviesRepositoryProvider,
      Provider<FavoriteMoviesRepository> favoriteMoviesRepositoryProvider) {
    this.moviesRepositoryProvider = moviesRepositoryProvider;
    this.favoriteMoviesRepositoryProvider = favoriteMoviesRepositoryProvider;
  }

  @Override
  public SearchMovieViewModel get() {
    return newInstance(moviesRepositoryProvider.get(), favoriteMoviesRepositoryProvider.get());
  }

  public static SearchMovieViewModel_Factory create(
      Provider<MoviesRepository> moviesRepositoryProvider,
      Provider<FavoriteMoviesRepository> favoriteMoviesRepositoryProvider) {
    return new SearchMovieViewModel_Factory(moviesRepositoryProvider, favoriteMoviesRepositoryProvider);
  }

  public static SearchMovieViewModel newInstance(MoviesRepository moviesRepository,
      FavoriteMoviesRepository favoriteMoviesRepository) {
    return new SearchMovieViewModel(moviesRepository, favoriteMoviesRepository);
  }
}
