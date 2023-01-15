package com.example.recyclev.sources.post

import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostsSource
import com.example.recyclev.model.user.User
import com.example.recyclev.sources.base.BaseRetrofitSource
import com.example.recyclev.sources.base.RetrofitConfig
import com.example.recyclev.sources.user.UsersApi

class RetrofitPostsSource (
    config: RetrofitConfig
): BaseRetrofitSource(config), PostsSource {

    private val postsApi =
        retrofit.create(PostsApi::class.java)

    override suspend fun getPosts(): List<Post> = wrapRetrofitExceptions {
        postsApi.getPosts()
    }

}