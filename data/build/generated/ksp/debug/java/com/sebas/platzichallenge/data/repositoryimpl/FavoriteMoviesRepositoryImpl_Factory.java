package com.sebas.platzichallenge.data.repositoryimpl;

import com.sebas.platzichallenge.data.datasource.FavoriteMoviesDataSource;
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
public final class FavoriteMoviesRepositoryImpl_Factory implements Factory<FavoriteMoviesRepositoryImpl> {
  private final Provider<FavoriteMoviesDataSource> dataSourceProvider;

  public FavoriteMoviesRepositoryImpl_Factory(
      Provider<FavoriteMoviesDataSource> dataSourceProvider) {
    this.dataSourceProvider = dataSourceProvider;
  }

  @Override
  public FavoriteMoviesRepositoryImpl get() {
    return newInstance(dataSourceProvider.get());
  }

  public static FavoriteMoviesRepositoryImpl_Factory create(
      Provider<FavoriteMoviesDataSource> dataSourceProvider) {
    return new FavoriteMoviesRepositoryImpl_Factory(dataSourceProvider);
  }

  public static FavoriteMoviesRepositoryImpl newInstance(FavoriteMoviesDataSource dataSource) {
    return new FavoriteMoviesRepositoryImpl(dataSource);
  }
}
