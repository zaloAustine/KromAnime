package com.zalo.kromanime.data.database


/**
Created by zaloaustine in 9/4/23.
 */
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zalo.kromanime.data.api.models.Trailer

class TrailerTypeConverter {

    @TypeConverter
    fun fromTrailer(trailer: Trailer): String {
        return Gson().toJson(trailer)
    }

    @TypeConverter
    fun toTrailer(trailerJson: String): Trailer {
        return Gson().fromJson(trailerJson, Trailer::class.java)
    }
}
