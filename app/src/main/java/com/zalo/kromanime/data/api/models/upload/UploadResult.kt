package com.zalo.kromanime.data.api.models.upload


/**
Created by zaloaustine in 9/4/23.
 */
sealed class UploadResult {
    data class Success(val response: UploadResponse) : UploadResult()
    data class Failure(val error: String) : UploadResult()
}
