package com.zalo.kromanime.ui.image_upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalo.kromanime.data.api.ApiService
import com.zalo.kromanime.data.api.models.upload.UploadResult
import com.zalo.kromanime.utils.FileUtils
import com.zalo.kromanime.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class ImageUploadViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _uploadResponse = SingleLiveEvent<UploadResult>()
    val uploadResponse: SingleLiveEvent<UploadResult>
        get() = _uploadResponse

    fun uploadImage(imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageFile = FileUtils.getImageMultiPart(imageUri)
                imageFile?.let { multiPart ->
                    val response = apiService.uploadImage(multiPart)
                    if (response.isSuccessful) {
                        val data = response.body()
                        _uploadResponse.postValue(data?.let { UploadResult.Success(it) }
                            ?: UploadResult.Failure("Upload failed")
                        )
                    } else {
                        _uploadResponse.postValue(UploadResult.Failure("Upload failed"))
                    }
                }
            } catch (e: Exception) {
                _uploadResponse.postValue(UploadResult.Failure("An Error Occurred Try Again"))
            }
        }
    }
}
