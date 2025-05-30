package com.sebas.platzichallenge.data.datasource.paging;

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
public final class MoviePagingDataSource_Factory implements Factory<MoviePagingDataSource> {
  private final Provider<MovieApiService> movieApiServiceProvider;

  private final Provider<String> queryProvider;

  public MoviePagingDataSource_Factory(Provider<MovieApiService> movieApiServiceProvider,
      Provider<String> queryProvider) {
    this.movieApiServiceProvider = movieApiServiceProvider;
    this.queryProvider = queryProvider;
  }

  @Override
  public MoviePagingDataSource get() {
    return newInstance(movieApiServiceProvider.get(), queryProvider.get());
  }

  public static MoviePagingDataSource_Factory create(
      Provider<MovieApiService> movieApiServiceProvider, Provider<String> queryProvider) {
    return new MoviePagingDataSource_Factory(movieApiServiceProvider, queryProvider);
  }

  public static MoviePagingDataSource newInstance(MovieApiService movieApiService, String query) {
    return new MoviePagingDataSource(movieApiService, query);
  }
}
