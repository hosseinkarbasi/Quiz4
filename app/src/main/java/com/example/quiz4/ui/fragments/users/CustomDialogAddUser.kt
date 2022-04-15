package com.example.quiz4.ui.fragments.users

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quiz4.App
import com.example.quiz4.R
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.ui.fragments.savedusers.SavedUsersViewModel
import com.google.android.material.textfield.TextInputEditText


class CustomDialogAddUser : DialogFragment(R.layout.add_user_dialog) {

    private val navController by lazy { findNavController() }
    private val viewModel: SavedUsersViewModel by viewModels(factoryProducer = {
        UserListViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
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
        val btnPositive = view.findViewById<AppCompatButton>(R.id.btnPositive)
        val btnNegative = view.findViewById<AppCompatButton>(R.id.btnNegative)
        val checkBoxMovie = view.findViewById<CheckBox>(R.id.movie)
        val checkBoxCoding = view.findViewById<CheckBox>(R.id.coding)
        val hobbies = mutableListOf<String>()

        btnPositive.setOnClickListener {
            if (checkBoxMovie.isChecked) hobbies.add("movie")
            if (checkBoxCoding.isChecked) hobbies.add("coding")
            val user = UserInfo(
                firsName.text.toString(),
                lastName.text.toString(),
                nationalCode.text.toString(),
                hobbies as ArrayList<String>
            )

            viewModel.createUser(user)

                navController.navigate(
                    CustomDialogAddUserDirections.actionCustomDialogAddUserToUsersList()
                )
        }
        btnNegative.setOnClickListener {
            dismiss()
        }
    }
}