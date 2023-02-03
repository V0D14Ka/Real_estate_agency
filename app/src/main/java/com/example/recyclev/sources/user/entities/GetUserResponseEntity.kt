package com.example.recyclev.sources.user.entities

import com.example.recyclev.model.user.User

data class GetUserResponseEntity(
    val Id: Long,
    val username: String,
    val phone: String,
    val avatar: String
) {
    fun toUser(): User = User(
        email = null,
        phone = phone,
        favorite_adverts = null,
        username = username,
        id = Id,
        avatar = avatar
    )
}