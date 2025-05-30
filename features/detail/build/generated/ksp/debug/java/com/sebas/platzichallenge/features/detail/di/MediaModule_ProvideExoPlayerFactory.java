package com.sebas.platzichallenge.features.detail.di;

import android.content.Context;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class MediaModule_ProvideExoPlayerFactory implements Factory<ExoPlayer> {
  private final MediaModule module;

  private final Provider<Context> contextProvider;

  public MediaModule_ProvideExoPlayerFactory(MediaModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public ExoPlayer get() {
    return provideExoPlayer(module, contextProvider.get());
  }

  public static MediaModule_ProvideExoPlayerFactory create(MediaModule module,
      Provider<Context> contextProvider) {
    return new MediaModule_ProvideExoPlayerFactory(module, contextProvider);
  }

  public static ExoPlayer provideExoPlayer(MediaModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideExoPlayer(context));
  }
}
