package com.sebas.platzichallenge.data.repositoryimpl;

import com.sebas.platzichallenge.data.datasource.MoviesDataSource;
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
public final class MoviesRepositoryImpl_Factory implements Factory<MoviesRepositoryImpl> {
  private final Provider<MovieApiService> apiServiceProvider;

  private final Provider<MoviesDataSource> moviesDataSourceProvider;

  public MoviesRepositoryImpl_Factory(Provider<MovieApiService> apiServiceProvider,
      Provider<MoviesDataSource> moviesDataSourceProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.moviesDataSourceProvider = moviesDataSourceProvider;
  }

  @Override
  public MoviesRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), moviesDataSourceProvider.get());
  }

  public static MoviesRepositoryImpl_Factory create(Provider<MovieApiService> apiServiceProvider,
      Provider<MoviesDataSource> moviesDataSourceProvider) {
    return new MoviesRepositoryImpl_Factory(apiServiceProvider, moviesDataSourceProvider);
  }

  public static MoviesRepositoryImpl newInstance(MovieApiService apiService,
      MoviesDataSource moviesDataSource) {
    return new MoviesRepositoryImpl(apiService, moviesDataSource);
  }
}
