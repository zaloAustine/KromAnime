package com.zalo.kromanime.utils

import android.net.Uri
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

/**
Created by zaloaustine in 9/5/23.
 */
object FileUtils {

    fun getImageMultiPart(imageUri: Uri): MultipartBody.Part? {
        imageUri.path?.let {
            val imageFile = File(it)
            checkImageFileExists(imageFile)
            if (imageFile.exists()) {
                val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                return MultipartBody.Part.createFormData("image", imageFile.name, requestBody)
            }
        }
        return null
    }

    private fun checkImageFileExists(file: File) {
        if (!file.exists()) {
            throw Exception("Image file not found")
        }
    }
}