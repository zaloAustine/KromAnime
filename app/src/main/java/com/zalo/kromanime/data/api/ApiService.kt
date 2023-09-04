package com.zalo.kromanime.data.api

import AnimeResponse
import com.zalo.kromanime.data.api.models.upload.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
Created by zaloaustine in 9/4/23.
 */
interface ApiService {
    @GET("/v4/top/anime")
    suspend fun getAnimeList(): Response<AnimeResponse>

    @Multipart
    @POST("https://api.trace.moe/search")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<UploadResponse>
}