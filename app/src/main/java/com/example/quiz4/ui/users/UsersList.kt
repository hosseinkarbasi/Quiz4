package com.example.quiz4.ui.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.App
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.R
import com.example.quiz4.data.local.model.User
import com.example.quiz4.databinding.UsersListBinding
import com.example.quiz4.ui.CustomViewModelFactory
import com.example.quiz4.ui.UsersListViewModel
import com.example.quiz4.util.SwipeG
import com.example.quiz4.util.collectWithRepeatOnLifecycle

class UsersList : Fragment(R.layout.users_list) {

    private var listUsers = mutableListOf<UsersListItem>()
    private lateinit var myAdapter: RecyclerAdapter
    private lateinit var binding: UsersListBinding
    val navController by lazy { findNavController() }
    private val viewModel: UsersListViewModel by viewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!

        setupRecyclerView()
        showUsers()
        swipe()

        binding.AddUser.setOnClickListener {
            navController.navigate(UsersListDirections.actionUsersListToCustomDialogAddUser())
        }
    }

    private fun showUsers() {
        viewModel.getUsers()
        viewModel.userList.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            listUsers.clear()
            listUsers.addAll(it)
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        myAdapter = RecyclerAdapter(listUsers)
        binding.recyclerViewMain.adapter = myAdapter
        binding.recyclerViewMain.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun swipe() {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val user = myAdapter.addToDataBase(viewHolder.bindingAdapterPosition)
                        viewModel.insertHobbies(user._id)
                        viewModel.insertUser(
                            User(
                                user._id,
                                user.firstName,
                                user.lastName,
                                user.nationalCode
                            )
                        )
                    }
                    ItemTouchHelper.RIGHT -> {
                        val user = myAdapter.addToDataBase(viewHolder.bindingAdapterPosition)
                        navController.navigate(UsersListDirections.actionUsersListToShowInfo((user._id)))
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.recyclerViewMain)
    }
}