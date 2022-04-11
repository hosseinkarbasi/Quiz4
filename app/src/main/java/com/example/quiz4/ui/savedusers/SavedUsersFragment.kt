package com.example.quiz4.ui.savedusers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.App
import com.example.quiz4.R
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.databinding.UsersSavedBinding
import com.example.quiz4.ui.CustomViewModelFactory
import com.example.quiz4.ui.UsersListViewModel
import com.example.quiz4.util.SwipeG
import com.example.quiz4.util.collectWithRepeatOnLifecycle

class SavedUsersFragment : Fragment(R.layout.users_saved) {

    private var listUsers = mutableListOf<UserWithHobbies>()
    private lateinit var myAdapter: RecyclerAdapterSavedUser
    private lateinit var binding: UsersSavedBinding
    private val viewModel: UsersListViewModel by viewModels(factoryProducer = {
        CustomViewModelFactory((requireActivity().application as App).serviceLocator.userRepository)
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UsersSavedBinding.bind(view)

        setupRecyclerView()
        showUsersSaved()
        swipe()
    }


    private fun showUsersSaved() {
        viewModel.getUsersFromDataBase()
        viewModel.getUsersFromDataBase.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            listUsers.clear()
            listUsers.addAll(it)
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun swipe() {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        val user = myAdapter.swipe(viewHolder.bindingAdapterPosition)
                        viewModel.deleteUser(user.id)
                        myAdapter.deleteUserFromList(viewHolder.bindingAdapterPosition)
                        myAdapter.notifyDataSetChanged()
                    }
                    ItemTouchHelper.LEFT -> {
                        val user = myAdapter.swipe(viewHolder.bindingAdapterPosition)
                        val customDialog = CustomDialogEditUser(user)
                        customDialog.show(childFragmentManager,"custom")
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.savedUsersRv)
    }

    private fun setupRecyclerView() {
        myAdapter = RecyclerAdapterSavedUser(listUsers)
        binding.savedUsersRv.adapter = myAdapter
        binding.savedUsersRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        showUsersSaved()
        super.onResume()
    }
}