package com.example.quiz4.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val firstName: String?,
    val lastName: String?,
    val nationalCode: String?,
    val hobbies: ArrayList<String>
) : Parcelable
