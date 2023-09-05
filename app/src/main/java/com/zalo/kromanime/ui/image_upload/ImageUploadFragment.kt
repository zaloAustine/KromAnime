package com.zalo.kromanime.ui.image_upload

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.zalo.kromanime.data.api.models.upload.UploadResponse
import com.zalo.kromanime.data.api.models.upload.UploadResult
import com.zalo.kromanime.databinding.FragmentImageUploadBinding
import com.zalo.kromanime.utils.InternetUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageUploadFragment : Fragment() {

    private var _binding: FragmentImageUploadBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ImageUploadViewModel by viewModels()

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                binding.uploadProgressBar.visibility = View.VISIBLE
                viewModel.uploadImage(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        activity?.title = "Image Upload"
        binding.buttonSelectImage.setOnClickListener {
            pickImage()
        }

        binding.buttonTakeImage.setOnClickListener {
            takeImage()
        }
        InternetUtils.checkAndShowRetrySnackbar(this){}
    }

    private fun initObservers() {
        viewModel.uploadResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UploadResult.Success -> {
                    val data = result.response
                    Snackbar.make(binding.root, "Image Uploaded", Snackbar.LENGTH_SHORT).show()
                    showUploadedImage(data)
                }
                is UploadResult.Failure -> {
                    val errorMessage = result.error
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
                    binding.uploadProgressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .galleryOnly()
            .compress(1024)
            .crop()
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                pickImageLauncher.launch(intent)
            }
    }

    private fun takeImage() {
        ImagePicker.with(this)
            .cameraOnly()
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                pickImageLauncher.launch(intent)
            }
    }

    private fun showUploadedImage(data: UploadResponse) {
        data.result[0].let {
            binding.dottedSquareView.setImageUrl(it.image)
            binding.fileNameTextView.text = it.filename
            binding.uploadProgressBar.visibility = View.GONE
        }
    }
}