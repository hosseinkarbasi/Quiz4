package com.example.quiz4.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz4.data.remote.model.UsersListItem
import com.example.quiz4.databinding.ShowUserBinding

class RecyclerAdapter(private var homeFeed: List<UsersListItem>) :
    RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>() {

    lateinit var itemClick: ItemClick

    fun addToDataBase(i: Int): UsersListItem {
        return homeFeed[i]
    }

    inner class CustomViewHolder(private var binding: ShowUserBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(position: Int) {
            binding.tvFirstName.text = homeFeed[position].firstName
            binding.tvLastName.text = homeFeed[position].lastName
            binding.tvNationalCode.text = homeFeed[position].nationalCode
        }

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            itemClick.viewClick(adapterPosition, p0)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ShowUserBinding.inflate(inflater, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeFeed.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(position)
    }

    interface ItemClick {
        fun viewClick(position: Int, v: View?)
    }

    fun setItemUserClick(itemClick: ItemClick) {
        this.itemClick = itemClick
    }
}