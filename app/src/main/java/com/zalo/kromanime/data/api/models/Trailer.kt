package com.zalo.kromanime.data.api.models

import androidx.room.TypeConverters
import com.zalo.kromanime.data.database.ImagesUrlsTypeConverter

data class Trailer(
    val embed_url: String,
    val url: String,
    val youtube_id: String,
    @TypeConverters(ImagesUrlsTypeConverter::class)
    val images: ImagesUrls?
)