package com.sebas.platzichallenge.data.datasource.implementation;

import com.sebas.platzichallenge.data.datasource.local.dao.FavoriteMovieDao;
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
public final class FavoriteMoviesDataSourceImpl_Factory implements Factory<FavoriteMoviesDataSourceImpl> {
  private final Provider<FavoriteMovieDao> favoriteMovieDaoProvider;

  public FavoriteMoviesDataSourceImpl_Factory(Provider<FavoriteMovieDao> favoriteMovieDaoProvider) {
    this.favoriteMovieDaoProvider = favoriteMovieDaoProvider;
  }

  @Override
  public FavoriteMoviesDataSourceImpl get() {
    return newInstance(favoriteMovieDaoProvider.get());
  }

  public static FavoriteMoviesDataSourceImpl_Factory create(
      Provider<FavoriteMovieDao> favoriteMovieDaoProvider) {
    return new FavoriteMoviesDataSourceImpl_Factory(favoriteMovieDaoProvider);
  }

  public static FavoriteMoviesDataSourceImpl newInstance(FavoriteMovieDao favoriteMovieDao) {
    return new FavoriteMoviesDataSourceImpl(favoriteMovieDao);
  }
}
