package com.example.recyclev.model.post

data class Post (
    val id: Long,
    val url: String,
    val advert_type: String,
    val city: String,
    val street: String,
    val latitude: String,
    val longitude: String,
    val floor: Long,
    val phone: String,
    val title: String,
    var is_favorite: Boolean,
    val sold: Boolean,
    val owner: String,
    val price: Long,
    val preview: String,
    val images: Array<Image>,
    val address: String,
    val description: String,
    val date: String
)

data class Image(
    val image: String
)