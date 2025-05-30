package com.sebas.platzichallenge.data.di;

import com.sebas.platzichallenge.data.datasource.remote.MovieApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class RemoteModule_ProvideMovieApiServiceFactory implements Factory<MovieApiService> {
  private final RemoteModule module;

  private final Provider<Retrofit> retrofitProvider;

  public RemoteModule_ProvideMovieApiServiceFactory(RemoteModule module,
      Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public MovieApiService get() {
    return provideMovieApiService(module, retrofitProvider.get());
  }

  public static RemoteModule_ProvideMovieApiServiceFactory create(RemoteModule module,
      Provider<Retrofit> retrofitProvider) {
    return new RemoteModule_ProvideMovieApiServiceFactory(module, retrofitProvider);
  }

  public static MovieApiService provideMovieApiService(RemoteModule instance, Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(instance.provideMovieApiService(retrofit));
  }
}
