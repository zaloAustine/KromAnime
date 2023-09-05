package com.zalo.kromanime.ui.image_upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalo.kromanime.data.api.ApiService
import com.zalo.kromanime.data.api.models.upload.UploadResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@HiltViewModel
class ImageUploadViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _uploadResponse = MutableLiveData<UploadResult>()
    val uploadResponse: LiveData<UploadResult>
        get() = _uploadResponse

    fun uploadImage(imageUri: Uri) {
        imageUri.path?.let {
            val imageFile = File(it)
            if (imageFile.exists()) {
                val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart =
                    MultipartBody.Part.createFormData("image", imageFile.name, requestBody)

                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        checkImageFileExists(imageFile)
                        val response = apiService.uploadImage(imagePart)

                        if (response.isSuccessful) {
                            val data = response.body()
                            _uploadResponse.postValue(data?.let { UploadResult.Success(it) }
                                ?: UploadResult.Failure("Upload failed")
                            )
                        } else {
                            _uploadResponse.postValue(UploadResult.Failure("Upload failed"))
                        }
                    } catch (e: Exception) {
                        _uploadResponse.postValue(UploadResult.Failure("An Error Occurred Try Again"))
                    }
                }
            }
        }
    }

    private fun checkImageFileExists(file: File) {
        if (!file.exists()) {
            throw Exception("Image file not found")
        }
    }

}
