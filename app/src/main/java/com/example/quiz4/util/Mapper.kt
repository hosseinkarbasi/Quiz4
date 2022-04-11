package com.example.quiz4.util

import com.example.quiz4.data.local.model.Hobie
import com.example.quiz4.data.local.model.User
import com.example.quiz4.data.remote.model.UsersListItem
import kotlin.random.Random

object Mapper {

    fun transformToHobie(userResponse: UsersListItem): List<Hobie> {
        return userResponse.hobbies.map {
            Hobie(Random.nextInt(), it, userResponse._id)
        }
    }

    fun transformToUserResponse(user: User, hobbies: List<Hobie>): UsersListItem {
        return UsersListItem(
            _id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            nationalCode = user.nationalCode,
            hobbies.map {
                it.name
            },
            ""
        )
    }
}