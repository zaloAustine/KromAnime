package com.zalo.kromanime.data.api.models.upload

data class Result(
    val anilist: Int,
    val filename: String,
    val from: Double,
    val image: String,
    val similarity: Double,
    val to: Double,
    val video: String
)