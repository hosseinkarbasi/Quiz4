package com.example.quiz4.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("nationalCode") val nationalCode: String?,
    @SerializedName("hobbies") val hobbies: ArrayList<String>,
)
