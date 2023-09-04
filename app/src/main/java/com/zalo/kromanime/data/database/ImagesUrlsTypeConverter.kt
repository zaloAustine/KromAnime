package com.zalo.kromanime.data.database


/**
Created by zaloaustine in 9/4/23.
 */
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zalo.kromanime.data.api.models.ImagesUrls

object ImagesUrlsTypeConverter {
    @JvmStatic
    @TypeConverter
    fun fromImagesUrls(imagesUrls: ImagesUrls): String {
        return Gson().toJson(imagesUrls)
    }

    @JvmStatic
    @TypeConverter
    fun toImagesUrls(imagesUrlsString: String): ImagesUrls {
        return Gson().fromJson(imagesUrlsString, ImagesUrls::class.java)
    }
}
