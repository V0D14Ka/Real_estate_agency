package com.example.recyclev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostService
import com.example.recyclev.model.post.PostsListener

class PostsListViewModel(
    private var postService : PostService
) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    private val listener: PostsListener = {
        _posts.value = it
    }

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        postService.removeListener(listener)
    }

    fun loadPosts() {
        postService.addListener(listener)
    }

    fun deletePost(post: Post) {
        postService.deletePost(post)
    }
//    fun favoritePost(post: Post) {
//
//    }
}