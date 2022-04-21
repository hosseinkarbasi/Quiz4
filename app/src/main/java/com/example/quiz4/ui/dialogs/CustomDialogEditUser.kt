//package com.example.quiz4.ui.dialogs
//
//import android.os.Bundle
//import android.view.*
//import android.widget.CheckBox
//import androidx.appcompat.widget.AppCompatButton
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.quiz4.R
//import com.example.quiz4.data.local.model.User
//import com.example.quiz4.data.remote.model.UserInfo
//import com.example.quiz4.ui.fragments.savedusers.SavedUsersViewModel
//import com.google.android.material.textfield.TextInputEditText
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class CustomDialogEditUser() :
//    DialogFragment(R.layout.add_user_dialog) {
//
////    private val args by navArgs<CustomDialogEditUserArgs>()
//    private val navController by lazy { findNavController() }
//    private val viewModel by viewModels<SavedUsersViewModel>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.add_user_dialog, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupView(view)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//    }
//
//    private fun setupView(view: View) {
//        val firstName = view.findViewById<TextInputEditText>(R.id.EdFirstName)
//        val lastName = view.findViewById<TextInputEditText>(R.id.EdLastName)
//        val nationalCode = view.findViewById<TextInputEditText>(R.id.EdNatinalcode)
//        val btnPositive = view.findViewById<AppCompatButton>(R.id.btnPositive)
//        val btnNegative = view.findViewById<AppCompatButton>(R.id.btnNegative)
//        val checkBoxMovie = view.findViewById<CheckBox>(R.id.movie)
//        val checkBoxCoding = view.findViewById<CheckBox>(R.id.coding)
//        val hobbies = mutableListOf<String>()
//
//        btnPositive.text = "EDIT"
//
////        firstName.setText(args.user.firstName)
////        lastName.setText(args.user.lastName)
////        nationalCode.setText(args.user.nationalCode)
//
//        btnPositive.setOnClickListener {
//            if (checkBoxMovie.isChecked) hobbies.add("movie")
//            if (checkBoxCoding.isChecked) hobbies.add("coding")
//            val firstNameText = firstName.text.toString()
//            val lastNameText = lastName.text.toString()
//            val nationalCodeText = nationalCode.text.toString()
//
//            val user = User(
//                args.user.id,
//                firstNameText,
//                lastNameText,
//                nationalCodeText,
//            )
//            val sendUser = UserInfo(
//                firstNameText,
//                lastNameText,
//                nationalCodeText,
//                hobbies as ArrayList<String>
//            )
//
////            viewModel.createUser(sendUser)
//            viewModel.updateUser(user)
//            navController.navigate(
//                CustomDialogEditUserDirections.actionCustomDialogEditUserToSavedUsersFragment())
//        }
//        btnNegative.setOnClickListener {
//            dismiss()
//        }
//    }
//}