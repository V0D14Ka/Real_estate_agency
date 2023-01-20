package com.example.recyclev.sources.user.entities

import com.example.recyclev.model.user.User

data class GetUserResponseEntity(
    val Id: Long,
    val email: String,
    val username: String,
    val phone: String,
    val favorite_adverts: Array<String>
) {
    fun toUser(): User = User(
        email = email,
        phone = phone,
        favorite_adverts = favorite_adverts,
        username = username,
        id = Id
    )
}