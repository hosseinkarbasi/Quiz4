package com.example.quiz4.ui.fragments.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.R
import com.example.quiz4.data.local.model.User
import com.example.quiz4.databinding.UsersListBinding
import com.example.quiz4.util.Result
import com.example.quiz4.util.SwipeG
import com.example.quiz4.util.collectWithRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersList : Fragment(R.layout.users_list) {

    private lateinit var binding: UsersListBinding
    val navController by lazy { findNavController() }
    private val viewModel by viewModels<UsersListViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!

        showUsers()

        binding.AddUser.setOnClickListener {
            navController.navigate(UsersListDirections.actionUsersListToCustomDialogAddUser())
        }
    }


    private fun showUsers() = binding.apply {
        val myAdapter = RecyclerAdapter()
        viewModel.getUsers.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loading.visible()
                }
                is Result.Error -> {
                    retry.visible()
                    loading.visible()
                    retry.setOnClickListener {
                        viewModel.retry()
                    }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    loading.gone()
                    myAdapter.submitList(it.data)
                    recyclerViewMain.adapter = myAdapter
                }
            }
        }
        swipe(myAdapter)
    }

    private fun swipe(adapter: RecyclerAdapter) {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val user = adapter.addToDataBase(viewHolder.bindingAdapterPosition)
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
                        val user = adapter.addToDataBase(viewHolder.bindingAdapterPosition)
                        navController.navigate(UsersListDirections.actionUsersListToShowInfo((user._id)))
                    }
                }
            }
        }
        binding.recyclerViewMain.adapter = adapter
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.recyclerViewMain)
    }

    private fun View.visible() {
        visibility = View.VISIBLE
    }

    private fun View.gone() {
        visibility = View.GONE
    }
}