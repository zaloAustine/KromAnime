package com.zalo.kromanime.data.api.models


/**
Created by zaloaustine in 9/4/23.
 */
data class AnimeItem(
    val mal_id: Int?,
    val url: String?,
    val trailer: Trailer?,
    val approved: Boolean?,
    val title: String?,
    val title_english: String?,
    val title_japanese: String?,
    val type: String?,
    val episodes: Int?,
    val status: String?,
    val duration: String?,
    val rating: String?,
    val scored_by: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val season: String?,
    val year: Int?
)
