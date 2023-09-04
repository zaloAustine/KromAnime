package com.zalo.kromanime.data.api.models.upload

data class UploadResponse(
    val error: String,
    val frameCount: Int,
    val result: List<Result>
)