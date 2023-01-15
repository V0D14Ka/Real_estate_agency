package com.example.recyclev.model.post

data class Post (
    val id: Long,
    val url: String,
    val title: String,
    val phone: String,
    val owner: String,
    val price: Int,
    val preview: String,
    val images: Array<Image>,
    val address: String,
    val description: String,
    val date: String
)

data class Image(
    val image: String
)