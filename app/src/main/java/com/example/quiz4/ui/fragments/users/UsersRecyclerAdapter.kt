package com.example.quiz4.ui.fragments.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.databinding.ShowUserBinding

class UsersRecyclerAdapter() :
    ListAdapter<UsersListItem, UsersRecyclerAdapter.CustomViewHolder>(DiffCallBack()) {

    fun addToDataBase(id: Int): UsersListItem {
        return getItem(id)
    }

    inner class CustomViewHolder(private var binding: ShowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UsersListItem) {
            binding.tvFirstName.text = item.firstName
            binding.tvLastName.text = item.lastName
            binding.tvNationalCode.text = item.nationalCode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            ShowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallBack : DiffUtil.ItemCallback<UsersListItem>() {
        override fun areItemsTheSame(oldItem: UsersListItem, newItem: UsersListItem): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: UsersListItem, newItem: UsersListItem): Boolean {
            return oldItem == newItem
        }
    }
}