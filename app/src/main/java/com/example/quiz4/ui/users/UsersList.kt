package com.example.quiz4.ui.users

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quiz4.App
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.R
import com.example.quiz4.databinding.UsersListBinding
import com.example.quiz4.ui.CustomViewModelFactory

class UsersList : Fragment(R.layout.users_list) {

    private var listUsers = mutableListOf<UsersListItem>()
    private lateinit var myAdapter: RecyclerAdapter
    private lateinit var binding: UsersListBinding
    private val viewModel: UsersListViewModel by viewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })
    val navController by lazy { findNavController() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!

        setupRecyclerView()
        showUsers()

        binding.AddUser.setOnClickListener {
            showCreateUserDialog()
        }

        myAdapter.setItemUserClick(object : RecyclerAdapter.ItemClick {
            override fun viewClick(position: Int, v: View?) {
                navController.navigate(UsersListDirections.actionUsersListToShowInfo((listUsers[position]._id)))
            }
        })
    }

    private fun showCreateUserDialog() {
        val dialog = CustomDialogAddUser()
        dialog.show(childFragmentManager, "custom")
    }

    private fun showUsers() {
        viewModel.getUsers()
        viewModel.usersList.observe(viewLifecycleOwner) {
            listUsers.clear()
            listUsers.addAll(it)
            Log.d("hossein", it.toString())
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        myAdapter = RecyclerAdapter(listUsers)
        binding.recyclerViewMain.adapter = myAdapter
        binding.recyclerViewMain.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}