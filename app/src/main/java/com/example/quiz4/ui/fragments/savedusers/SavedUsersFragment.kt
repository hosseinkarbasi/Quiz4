package com.example.quiz4.ui.fragments.savedusers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.R
import com.example.quiz4.databinding.UsersSavedBinding
import com.example.quiz4.util.SwipeG
import com.example.quiz4.util.collectWithRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedUsersFragment : Fragment(R.layout.users_saved) {

    private lateinit var binding: UsersSavedBinding
    private val viewModel by viewModels<SavedUsersViewModel>()
    val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UsersSavedBinding.bind(view)

        showUsersSaved()
    }

    private fun showUsersSaved() {
        val myAdapter = RecyclerAdapterSavedUser()
        viewModel.getUsersFromDataBase()
        viewModel.getUsersFromDataBase.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            myAdapter.submitList(it)
            binding.savedUsersRv.adapter = myAdapter
        }
        swipe(myAdapter)
    }

    private fun swipe(myAdapter: RecyclerAdapterSavedUser) {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        val user = myAdapter.swipe(viewHolder.bindingAdapterPosition)
                        viewModel.deleteUser(user.id)
                    }
                    ItemTouchHelper.LEFT -> {
                        val user = myAdapter.swipe(viewHolder.bindingAdapterPosition)
                        navController.navigate(
                            SavedUsersFragmentDirections.actionSavedUsersFragmentToCustomDialogEditUser(
                                user
                            )
                        )
                    }
                }
            }
        }
        binding.savedUsersRv.adapter = myAdapter
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.savedUsersRv)
    }
}