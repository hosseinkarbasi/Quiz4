package com.example.quiz4.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String
)
