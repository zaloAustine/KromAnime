package com.zalo.kromanime.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

/**
Created by zaloaustine in 9/4/23.
 */
@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): PagingSource<Int, AnimeEntity>

    @Query("DELETE FROM anime_table")
    suspend fun clearAll()

    @Transaction
    @Query("SELECT * FROM anime_table WHERE title LIKE '%' || :query || '%' ORDER BY mal_id ASC")
    fun getFilteredAnime(query: String): PagingSource<Int, AnimeEntity>
}