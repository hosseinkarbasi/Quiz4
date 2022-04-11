package com.example.quiz4.ui.showusersinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.quiz4.App
import com.example.quiz4.R
import com.example.quiz4.databinding.ShowInfoBinding
import com.example.quiz4.ui.CustomViewModelFactory
import com.example.quiz4.ui.UsersListViewModel
import com.example.quiz4.util.collectWithRepeatOnLifecycle

class ShowInfo : Fragment(R.layout.show_info) {

    private lateinit var binding: ShowInfoBinding
    private val args by navArgs<ShowInfoArgs>()
    private val viewModel: UsersListViewModel by activityViewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ShowInfoBinding.bind(view)

        viewModel.showInfoUser(args.id)
        viewModel.showInfo.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            binding.tvFirstName.text = it.firstName
            binding.tvLastName.text = it.lastName
            binding.tvNationalCode.text = it.nationalCode
            binding.tvHobbie.text = it.hobbies.toString()

            Glide
                .with(requireContext())
                .load(it.image)
                .into(binding.imgProfile)
        }
    }
}