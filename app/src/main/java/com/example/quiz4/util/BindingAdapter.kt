package com.example.quiz4.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.quiz4.R

@BindingAdapter("imgUrl")
fun ImageView.setImage(url: String?) {
    Glide
        .with(this)
        .load(url)
        .placeholder(R.drawable.ic_baseline_add_circle_24)
        .into(this)
}