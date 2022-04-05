package com.example.quiz4.ui.users

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.quiz4.App
import com.example.quiz4.R
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.ui.CustomViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class CustomDialogAddUser : DialogFragment(R.layout.add_user_dialog) {

    private val viewModel: UsersListViewModel by activityViewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_user_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        val firsName = view.findViewById<TextInputEditText>(R.id.EdFirstName)
        val lastName = view.findViewById<TextInputEditText>(R.id.EdLastName)
        val nationalCode = view.findViewById<TextInputEditText>(R.id.EdNatinalcode)
        view.findViewById<AppCompatButton>(R.id.btnPositive).setOnClickListener {
            viewModel.createUser(
                UserInfo(
                    firsName.text.toString(),
                    lastName.text.toString(),
                    nationalCode.text.toString(),
                    arrayListOf("sport")
                )
            )
            dismiss()
        }
        view.findViewById<AppCompatButton>(R.id.btnNegative).setOnClickListener {
            dismiss()
        }
    }
}