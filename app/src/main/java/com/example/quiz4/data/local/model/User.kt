package com.example.quiz4.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String
) : Parcelable
