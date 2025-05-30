package com.sebas.platzichallenge.data.di

import android.content.Context
import androidx.room.Room
import com.sebas.platzichallenge.data.datasource.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    companion object {
        private const val APP_DATABASE_NAME = "platzi_challage_database"
    }


    @Singleton
    @Provides
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideFavoriteDao(
        database: AppDatabase
    ) = database.favoriteMovieDao()

}