package com.example.recyclev.sources.user.entities

import com.example.recyclev.model.user.User

data class GetUserResponseEntity(
    val id: Long,
    val email: String,
    val username: String,
    val createdAt: Long
) {
    fun toUser(): User = User(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )
}