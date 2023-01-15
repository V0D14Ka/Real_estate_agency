package com.example.recyclev.sources.post

import com.example.recyclev.model.post.Post
import com.example.recyclev.sources.user.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface PostsApi {

    @GET("adverts/")
    suspend fun getPosts(): List<Post>

}