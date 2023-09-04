package com.zalo.kromanime.data.api.models.mappers

import com.zalo.kromanime.data.api.models.animes.AnimeItem
import com.zalo.kromanime.data.database.AnimeEntity
/**
Created by zaloaustine in 9/4/23.
 */
fun AnimeItem.toAnimeEntity(): AnimeEntity {
    return AnimeEntity(
        mal_id = this.mal_id,
        url = this.url,
        approved = this.approved,
        title = this.title,
        titleEnglish = this.title_english,
        titleJapanese = this.title_japanese,
        type = this.type,
        episodes = this.episodes,
        status = this.status,
        duration = this.duration,
        rating = this.rating,
        scoredBy = this.scored_by,
        rank = this.rank,
        popularity = this.popularity,
        members = this.members,
        favorites = this.favorites,
        synopsis = this.synopsis,
        season = this.season,
        year = this.year,
        images = this.images
    )

}
    fun AnimeEntity.toAnimeItem(): AnimeItem {
        return AnimeItem(
            mal_id = this.mal_id,
            url = this.url,
            approved = this.approved,
            title = this.title,
            title_english = this.titleEnglish,
            title_japanese = this.titleJapanese,
            type = this.type,
            episodes = this.episodes,
            status = this.status,
            duration = this.duration,
            rating = this.rating,
            scored_by = this.scoredBy,
            rank = this.rank,
            popularity = this.popularity,
            members = this.members,
            favorites = this.favorites,
            synopsis = this.synopsis,
            season = this.season,
            year = this.year,
            images = this.images
        )

}
