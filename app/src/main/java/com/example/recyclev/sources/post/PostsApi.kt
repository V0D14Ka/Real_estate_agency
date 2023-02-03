package com.example.recyclev.sources.post

import com.example.recyclev.model.post.Post
import com.example.recyclev.sources.user.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostsApi {

    @GET("adverts/")
    suspend fun getPosts(): List<Post>

    @GET("adverts/{id}")
    suspend fun getPost( @Path("id") id: Long): Post

    @GET("adverts/{id}/?favorite=true")
    suspend fun likePost(@Path("id") id: Long) : Post

}