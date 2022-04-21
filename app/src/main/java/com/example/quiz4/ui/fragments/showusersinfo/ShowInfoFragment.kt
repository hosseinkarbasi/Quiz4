package com.example.quiz4.ui.fragments.showusersinfo

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.quiz4.R
import com.example.quiz4.databinding.ShowInfoBinding
import com.example.quiz4.util.Result
import com.example.quiz4.util.collectWithRepeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ShowInfoFragment : Fragment(R.layout.show_info) {

    var imageByteArray: ByteArray? = null
    private var _binding: ShowInfoBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<ShowInfoFragmentArgs>()
    private val viewModel by viewModels<ShowInfoVIewModel>()

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val imageUri = context?.contentResolver?.openInputStream(it)?.readBytes()
        imageByteArray = imageUri
        binding.imgProfile.setImageURI(it)
    }

    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        imageByteArray = it.toByteArray()
        binding.imgProfile.setImageBitmap(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)

        showDetailsUser()
        uploadImage()

        binding.uploadImage.setOnClickListener {
            viewModel.uploadImage(args.id, imageByteArray!!)
        }
    }

    private fun showDetailsUser() = binding.apply {
        viewModel.showInfoUser(args.id)
        viewModel.showInfo.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loading.visible()
                }
                is Result.Error -> {
                    loading.visible()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    loading.gone()
                    user = it.data
                }
            }
        }
    }

    private fun uploadImage() {
        binding.imgProfile.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Upload Image")
            builder.setMessage("Select Camera or Gallery")

            builder.setPositiveButton("Gallery") { dialog, _ ->
                selectImage.launch("image/*")
                dialog.dismiss()
            }
            builder.setNeutralButton("Camera") { dialog, _ ->
                camera.launch(null)
                dialog.dismiss()
            }
            builder.create().show()
        }
    }

    private fun Bitmap.toByteArray(): ByteArray {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG, 10, this)
            return toByteArray()
        }
    }

    private fun View.visible() {
        visibility = View.VISIBLE
    }

    private fun View.gone() {
        visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}