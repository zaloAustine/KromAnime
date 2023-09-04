package com.zalo.kromanime.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zalo.kromanime.data.api.models.ImagesUrls
import com.zalo.kromanime.data.api.models.Trailer


/**
Created by zaloaustine in 9/4/23.
 */
@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey
    val mal_id: Int?,
    val url: String?,
    @TypeConverters(TrailerTypeConverter::class)
    val trailer: Trailer?,
    val approved: Boolean?,
    val title: String?,
    val titleEnglish: String?,
    val titleJapanese: String?,
    val type: String?,
    val episodes: Int?,
    val status: String?,
    val duration: String?,
    val rating: String?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val season: String?,
    val year: Int?
)