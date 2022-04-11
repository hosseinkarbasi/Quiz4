package com.example.quiz4.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithHobbies(
    @Embedded val user: User,
    @Relation(parentColumn = "id", entityColumn = "user_id")
    val hobie: List<Hobie>
)
