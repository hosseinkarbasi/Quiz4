package com.example.quiz4.data.remote.model

data class UsersListItem(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String,
    val hobbies: List<String>,
    val image: String
)