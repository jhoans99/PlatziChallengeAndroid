package com.sebas.platzichallenge.data.di;

import android.content.Context;
import com.sebas.platzichallenge.data.datasource.local.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RoomModule_ProvideRoomDataBaseFactory implements Factory<AppDatabase> {
  private final RoomModule module;

  private final Provider<Context> contextProvider;

  public RoomModule_ProvideRoomDataBaseFactory(RoomModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public AppDatabase get() {
    return provideRoomDataBase(module, contextProvider.get());
  }

  public static RoomModule_ProvideRoomDataBaseFactory create(RoomModule module,
      Provider<Context> contextProvider) {
    return new RoomModule_ProvideRoomDataBaseFactory(module, contextProvider);
  }

  public static AppDatabase provideRoomDataBase(RoomModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideRoomDataBase(context));
  }
}
