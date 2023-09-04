package com.zalo.kromanime.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
Created by zaloaustine in 9/4/23.
 */
@Database(entities = [AnimeEntity::class], version = 2, exportSchema = false)
@TypeConverters(ImagesTypeConverter::class)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}