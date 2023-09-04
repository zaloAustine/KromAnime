package com.zalo.kromanime.di

import android.app.Application
import androidx.room.Room
import com.zalo.kromanime.data.database.AnimeDao
import com.zalo.kromanime.data.database.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
Created by zaloaustine in 9/4/23.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAnimeDatabase(application: Application): AnimeDatabase {
        return Room.databaseBuilder(
            application,
            AnimeDatabase::class.java,
            "anime_database"
        ).build()
    }

    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao {
        return database.animeDao()
    }
}