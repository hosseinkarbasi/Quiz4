package com.example.quiz4.ui.fragments.savedusers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.R
import com.example.quiz4.data.local.model.User
import com.example.quiz4.databinding.UsersSavedBinding
import com.example.quiz4.ui.dialogs.LocalUserDialog
import com.example.quiz4.util.SwipeG
import com.example.quiz4.util.collectWithRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedUsersFragment : Fragment(R.layout.users_saved) {

    private var _binding: UsersSavedBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SavedUsersViewModel>()
    private lateinit var myAdapter: RecyclerAdapterSavedUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = UsersSavedBinding.bind(view)

        initRecyclerView()
        showUsersSaved()
        swipe(myAdapter)
    }

    private fun showUsersSaved() {
        viewModel.getUsersFromDataBase.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            myAdapter.submitList(it)
        }
    }

    private fun swipe(myAdapter: RecyclerAdapterSavedUser) {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = myAdapter.swipe(viewHolder.bindingAdapterPosition)

                when (direction) {
                    ItemTouchHelper.RIGHT -> {

                        viewModel.deleteUser(user.id)
                    }
                    ItemTouchHelper.LEFT -> {

                        val dialog = LocalUserDialog {
                            viewModel.createNewUser(it)
                            viewModel.updateUser(
                                User(
                                    user.id,
                                    it.firstName.toString(),
                                    it.lastName.toString(),
                                    it.nationalCode.toString()
                                )
                            )
                        }
                        dialog.show(childFragmentManager, "EditUserDialog")
                        myAdapter.notifyItemChanged(viewHolder.bindingAdapterPosition)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.savedUsersRv)
    }

    private fun initRecyclerView() {
        myAdapter = RecyclerAdapterSavedUser()
        binding.savedUsersRv.adapter = myAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.savedUsersRv.adapter = null
        _binding = null
    }
}