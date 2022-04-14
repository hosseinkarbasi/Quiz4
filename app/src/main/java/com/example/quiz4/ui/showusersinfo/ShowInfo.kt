package com.example.quiz4.ui.showusersinfo

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.quiz4.App
import com.example.quiz4.R
import com.example.quiz4.databinding.ShowInfoBinding
import com.example.quiz4.ui.CustomViewModelFactory
import com.example.quiz4.ui.UsersListViewModel
import com.example.quiz4.util.Result
import com.example.quiz4.util.collectWithRepeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream

class ShowInfo : Fragment(R.layout.show_info) {
    var imageByteArray: ByteArray? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val imageUri = context?.contentResolver?.openInputStream(it)?.readBytes()
        imageByteArray = imageUri
        binding.imgProfile.setImageURI(it)
    }

    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        imageByteArray = it.toByteArray()
        binding.imgProfile.setImageBitmap(it)
    }

    private lateinit var binding: ShowInfoBinding
    private val args by navArgs<ShowInfoArgs>()
    private val viewModel: UsersListViewModel by activityViewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!

        showDetailsUser()
        uploadImage()

        binding.uploadImage.setOnClickListener {
            viewModel.uploadImage(args.id, imageByteArray!!)
        }
    }

    private fun showDetailsUser() {
        viewModel.showInfoUser(args.id)
        viewModel.showInfo.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.loading.visibility = View.GONE
                    binding.user = it.data
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
}