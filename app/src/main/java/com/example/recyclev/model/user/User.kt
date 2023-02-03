package com.example.recyclev.model.user

data class User(
    val id: Long,
    val username: String,
    val email: String?,
    val phone: String,
    val favorite_adverts: Array<String>?,
    val createdAt: Long = UNKNOWN_CREATED_AT
) {
    companion object {
        const val UNKNOWN_CREATED_AT = 0L
    }
}