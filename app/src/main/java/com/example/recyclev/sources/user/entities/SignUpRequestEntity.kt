package com.example.recyclev.sources.user.entities

data class SignUpRequestEntity(
    val email: String,
    val username: String,
    val phone: String,
    val password: String
)