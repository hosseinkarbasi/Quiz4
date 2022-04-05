package com.example.quiz4.data.remote.model

import com.google.gson.annotations.SerializedName

data class UsersListItem(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String,
    val hobbies: MutableList<String>,
    val image: String
)