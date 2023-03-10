package com.example.recyclev.screens.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostRepository
import com.example.recyclev.utils.MutableUnitLiveEvent
import com.example.recyclev.utils.publishEvent
import com.example.recyclev.utils.share

class PostsDetailsViewModel(
    private val postRepository: PostRepository
): ViewModel() {

    private val _postDetails = MutableLiveData<Post>()
    val postDetails: LiveData<Post> = _postDetails


    fun loadPost(postId: Long) {
        if ( _postDetails.value != null ) return
        try {
            _postDetails.value = postRepository.getById(postId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}