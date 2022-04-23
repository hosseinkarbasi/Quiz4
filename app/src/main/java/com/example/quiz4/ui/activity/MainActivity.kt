package com.example.quiz4.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quiz4.R
import com.example.quiz4.data.remote.model.UserInfo
import com.example.quiz4.databinding.ActivityMainBinding
import com.example.quiz4.ui.dialogs.UserDialog
import com.example.quiz4.ui.fragments.users.UsersListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserDialog.NoticeDialogListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UsersListViewModel>()

    private val navController by lazy {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.AddUser.setOnClickListener {
            val action = UserDialog()
            action.show(supportFragmentManager, "AddUser")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, userInfo: UserInfo) {
        viewModel.createUser(userInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}