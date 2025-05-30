package com.sebas.platzichallenge.data.datasource.implementation;

import com.sebas.platzichallenge.data.datasource.remote.MovieApiService;
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
public final class MoviesDataSourceImpl_Factory implements Factory<MoviesDataSourceImpl> {
  private final Provider<MovieApiService> moviesApiServiceProvider;

  public MoviesDataSourceImpl_Factory(Provider<MovieApiService> moviesApiServiceProvider) {
    this.moviesApiServiceProvider = moviesApiServiceProvider;
  }

  @Override
  public MoviesDataSourceImpl get() {
    return newInstance(moviesApiServiceProvider.get());
  }

  public static MoviesDataSourceImpl_Factory create(
      Provider<MovieApiService> moviesApiServiceProvider) {
    return new MoviesDataSourceImpl_Factory(moviesApiServiceProvider);
  }

  public static MoviesDataSourceImpl newInstance(MovieApiService moviesApiService) {
    return new MoviesDataSourceImpl(moviesApiService);
  }
}
