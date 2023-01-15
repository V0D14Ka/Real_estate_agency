package com.example.recyclev.sources

import com.example.recyclev.model.post.PostsSource
import com.example.recyclev.model.user.UsersSource

interface SourcesProvider {
    fun getUsersSource(): UsersSource
    fun getPostsSource(): PostsSource
}