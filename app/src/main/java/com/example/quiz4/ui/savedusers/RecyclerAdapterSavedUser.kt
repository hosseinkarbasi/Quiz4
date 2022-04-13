package com.example.quiz4.ui.savedusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.databinding.ShowInfoSavedUsersBinding

class RecyclerAdapterSavedUser() :
    ListAdapter<UserWithHobbies, RecyclerAdapterSavedUser.CustomViewHolder>(DiffCallBack()) {

    fun swipe(id: Int): User {
        return getItem(id).user
    }

    inner class CustomViewHolder(private var binding: ShowInfoSavedUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserWithHobbies) {

            binding.tvFirstName.text = item.user.firstName
            binding.tvLastName.text = item.user.lastName
            binding.tvNationalCode.text = item.user.nationalCode
            binding.tvHobbie.text = item.hobie.map {
                it.name
            }.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            ShowInfoSavedUsersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallBack : DiffUtil.ItemCallback<UserWithHobbies>() {
        override fun areItemsTheSame(oldItem: UserWithHobbies, newItem: UserWithHobbies): Boolean {
            return oldItem.user.id == newItem.user.id
        }

        override fun areContentsTheSame(
            oldItem: UserWithHobbies,
            newItem: UserWithHobbies
        ): Boolean {
            return oldItem.user.firstName == newItem.user.firstName &&
                    oldItem.user.lastName == newItem.user.lastName &&
                    oldItem.user.nationalCode == newItem.user.nationalCode
        }

    }
}