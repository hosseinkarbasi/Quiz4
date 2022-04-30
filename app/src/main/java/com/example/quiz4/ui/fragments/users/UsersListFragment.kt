package com.example.quiz4.ui.fragments.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
class UsersListFragment : Fragment(R.layout.users_list) {

    private var _binding: UsersListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UsersListViewModel>()
    private lateinit var myAdapterUsers: UsersRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = UsersListBinding.bind(view)

        initRecyclerView()
        showUsers()
        swipe(myAdapterUsers)
    }

    private fun showUsers() = binding.apply {
        viewModel.retry()
        viewModel.getUsers.collectWithRepeatOnLifecycle(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loading.visible()
                    loading.playAnimation()
                    retry.gone()
                }
                is Result.Error -> {
                    retry.visible()
                    loading.visible()
                    loading.playAnimation()
                    retry.setOnClickListener { viewModel.retry() }
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    retry.gone()
                    loading.gone()
                    loading.pauseAnimation()
                    myAdapterUsers.submitList(it.data)
                }
            }
        }
    }

    private fun initRecyclerView() {
        myAdapterUsers = UsersRecyclerAdapter()
        binding.recyclerViewMain.adapter = myAdapterUsers
    }

    private fun swipe(adapterUsers: UsersRecyclerAdapter) {
        val swipe = object : SwipeG(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val user = adapterUsers.addToDataBase(viewHolder.bindingAdapterPosition)
                        viewModel.insertHobbies(user._id)
                        viewModel.insertUser(
                            User(
                                user._id,
                                user.firstName,
                                user.lastName,
                                user.nationalCode
                            )
                        )
                        binding.recyclerViewMain.adapter?.notifyItemChanged(viewHolder.bindingAdapterPosition)
                    }
                    ItemTouchHelper.RIGHT -> {
                        val user = adapterUsers.addToDataBase(viewHolder.bindingAdapterPosition)
                        val action =
                            UsersListFragmentDirections.actionUsersListToShowInfo((user._id))
                        findNavController().navigate(action)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(binding.recyclerViewMain)
    }

    private fun View.visible() {
        visibility = View.VISIBLE
    }

    private fun View.gone() {
        visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewMain.adapter = null
        _binding = null
    }
}

