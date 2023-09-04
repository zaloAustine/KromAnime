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
        binding.buttonSelectImage.setOnClickListener {
            pickImage()
        }
    }

    private fun initObservers() {
        viewModel.uploadResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UploadResult.Success -> {
                    val data = result.response
                    showUploadedImage(data)
                }
                is UploadResult.Failure -> {
                    val errorMessage = result.error
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                pickImageLauncher.launch(intent)
            }
    }

    private fun showUploadedImage(data: UploadResponse) {

    }

    override fun onResume() {
        super.onResume()
        pickImage()

    }
}