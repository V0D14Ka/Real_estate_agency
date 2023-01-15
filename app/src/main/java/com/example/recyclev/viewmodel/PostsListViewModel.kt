package com.example.recyclev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostRepository
import com.example.recyclev.model.post.PostsListener
import com.example.recyclev.utils.share
import kotlinx.coroutines.launch

class PostsListViewModel(
    private var postRepository: PostRepository
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts = _posts.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val listener: PostsListener = {
        _posts.value = it
    }

    init {
        getPostsFromApi()
    }

    fun getPostsFromApi() = viewModelScope.launch {
        try {
            showProgress()
            postRepository.getPostsFromApi()
            postRepository.addListener(listener)
            if (_posts.value.isNullOrEmpty()) State(emptyList = true)
        } catch (e: Exception){
            TODO()
        } finally {
            hideProgress()
        }
    }

    override fun onCleared() {
        super.onCleared()
        postRepository.removeListener(listener)
    }

    fun loadPosts() {
        postRepository.addListener(listener)
    }

    fun deletePost(post: Post) {
        postRepository.deletePost(post)
    }

    private fun showProgress() {
        _state.value = State(getPostInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(getPostInProgress = false)
    }

    data class State(
        val getPostInProgress: Boolean = false,
        val emptyList: Boolean = false
    ) {
        val showProgress: Boolean get() = getPostInProgress
        val enableViews: Boolean get() = !getPostInProgress
        val emptyListInfo: Boolean get() = !emptyList
    }
}