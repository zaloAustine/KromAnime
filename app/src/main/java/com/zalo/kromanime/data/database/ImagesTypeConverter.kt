package com.zalo.kromanime.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zalo.kromanime.data.api.models.animes.Images


/**
Created by zaloaustine in 9/4/23.
 */
class ImagesTypeConverter {
    @TypeConverter
    fun fromImagesType(imagesType: Images?): String? {
        return Gson().toJson(imagesType)
    }

    @TypeConverter
    fun toImagesType(imagesTypeString: String?): Images? {
        return Gson().fromJson(imagesTypeString, Images::class.java)
    }
}