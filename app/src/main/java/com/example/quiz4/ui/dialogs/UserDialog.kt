package com.example.quiz4.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.databinding.AddUserDialogBinding

class UserDialog() : DialogFragment() {

    private var _binding: AddUserDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, userInfo: UserInfo)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddUserDialogBinding.inflate(layoutInflater)
        val hobbies = mutableListOf<String>()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("Create") { _, _ ->

                    val newUser = UserInfo(
                        binding.EdFirstName.text.toString(),
                        binding.EdLastName.text.toString(),
                        binding.EdNatinalcode.text.toString(),
                        hobbies as ArrayList<String>
                    )

                    listener.onDialogPositiveClick(this, newUser)
                    dismiss()
                }
                .setNeutralButton("Cancel") { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                ("$context must implement NoticeDialogListener")
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}