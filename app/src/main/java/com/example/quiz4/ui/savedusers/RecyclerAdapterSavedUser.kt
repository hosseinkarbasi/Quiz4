package com.example.quiz4.ui.savedusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.local.model.UserWithHobbies
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.databinding.ShowInfoSavedUsersBinding
import com.example.quiz4.databinding.ShowUserBinding

class RecyclerAdapterSavedUser(private var homeFeed: MutableList<UserWithHobbies>) :
    RecyclerView.Adapter<RecyclerAdapterSavedUser.CustomViewHolder>() {


    fun swipe(id: Int): User {
        return homeFeed[id].user
    }

    fun deleteUserFromList(id: Int) {
        homeFeed.removeAt(id)
    }

    inner class CustomViewHolder(private var binding: ShowInfoSavedUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.tvFirstName.text = homeFeed[position].user.firstName
            binding.tvLastName.text = homeFeed[position].user.lastName
            binding.tvNationalCode.text = homeFeed[position].user.nationalCode
            binding.tvHobbie.text = homeFeed[position].hobie.map {
                it.name
            }.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ShowInfoSavedUsersBinding.inflate(inflater, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeFeed.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(position)
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