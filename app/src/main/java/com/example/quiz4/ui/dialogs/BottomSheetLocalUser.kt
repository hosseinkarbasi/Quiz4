package com.example.quiz4.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.databinding.BottomSheetLocalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetLocalUser(val user: User, private val userInfo: (UserInfo) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: BottomSheetLocalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLocalBinding.inflate(inflater, container, false)

        binding.EdFirstName.setText(user.firstName)
        binding.EdLastName.setText(user.lastName)
        binding.EdNatinalcode.setText(user.nationalCode)

        val hobbies = mutableListOf<String>()
        binding.btnPositive.setOnClickListener {
            if (binding.movie.isChecked) hobbies.add("movie")
            if (binding.coding.isChecked) hobbies.add("coding")
            val newUser = UserInfo(
                binding.EdFirstName.text.toString(),
                binding.EdLastName.text.toString(),
                binding.EdNatinalcode.text.toString(),
                hobbies as ArrayList<String>
            )
            userInfo(newUser)
            dismiss()
        }

        binding.btnNegative.setOnClickListener { dismiss() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}