package com.zalo.kromanime.data.api.models.animes

data class AnimeResponse(
    val pagination: Pagination,
    val data: List<AnimeItem>?
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: Items
)

data class Items(
    val count: Int,
    val total: Int,
    val per_page: Int
)



