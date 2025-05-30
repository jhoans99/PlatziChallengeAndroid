package com.sebas.platzichallenge.data.di;

import com.sebas.platzichallenge.data.datasource.local.dao.FavoriteMovieDao;
import com.sebas.platzichallenge.data.datasource.local.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RoomModule_ProvideFavoriteDaoFactory implements Factory<FavoriteMovieDao> {
  private final RoomModule module;

  private final Provider<AppDatabase> databaseProvider;

  public RoomModule_ProvideFavoriteDaoFactory(RoomModule module,
      Provider<AppDatabase> databaseProvider) {
    this.module = module;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FavoriteMovieDao get() {
    return provideFavoriteDao(module, databaseProvider.get());
  }

  public static RoomModule_ProvideFavoriteDaoFactory create(RoomModule module,
      Provider<AppDatabase> databaseProvider) {
    return new RoomModule_ProvideFavoriteDaoFactory(module, databaseProvider);
  }

  public static FavoriteMovieDao provideFavoriteDao(RoomModule instance, AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(instance.provideFavoriteDao(database));
  }
}
