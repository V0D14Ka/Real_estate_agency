package com.example.recyclev.sources.user.entities

import com.example.recyclev.model.user.User

data class GetUserResponseEntity(
    val email: String,
    val username: String,
) {
    fun toUser(): User = User(
        email = email,
        username = username,
    )
}