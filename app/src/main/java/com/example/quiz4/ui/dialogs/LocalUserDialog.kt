package com.example.quiz4.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.quiz4.R
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.databinding.AddUserDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LocalUserDialog(private val userInfo: (UserInfo) -> Unit) : DialogFragment() {

    private var _binding: AddUserDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddUserDialogBinding.inflate(layoutInflater)
        val hobbies = mutableListOf<String>()

        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it, R.style.RoundShapeTheme)
            builder.setView(binding.root)
                .setTitle("Edit User")
                .setPositiveButton("Edit") { _, _ ->
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
                .setNeutralButton("Cancel") { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}