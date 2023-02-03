package com.example.recyclev.screens.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.model.post.LikedPostsListener
import com.example.recyclev.model.post.Post
import com.example.recyclev.model.post.PostRepository
import com.example.recyclev.model.post.PostsListener
import com.example.recyclev.utils.share
import com.example.recyclev.viewmodel.requireValue
import kotlinx.coroutines.launch

class PostLikeViewModel(
    private var postRepository: PostRepository
) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts = _posts.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val listener: LikedPostsListener = {
        _posts.value = it
    }

    init {
        getLikedPosts()
    }

    fun getLikedPosts() = viewModelScope.launch {
        try {
            showProgress()
            postRepository.getLikedPosts()
            postRepository.addLikedListener(listener)
        } catch (e: Exception){
            apiFail()
        } finally {
            hideProgress()
        }
    }

    fun like(id: Long) = viewModelScope.launch {
        try {
            postRepository.LikePost(id)
        } catch (e: Exception){
            apiFail()
        }
        if (_posts.value.isNullOrEmpty()) _state.value = _state.requireValue().copy(emptyList = true)
        Log.e("asd", _posts.value.isNullOrEmpty().toString())
    }

    override fun onCleared() {
        super.onCleared()
        postRepository.removeLikedListener(listener)
    }

    private fun showProgress() {
        _state.value = _state.requireValue().copy(emptyList = false, getPostInProgress = true, apiFail = false)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(getPostInProgress = false)
        if (_posts.value.isNullOrEmpty())
            _state.value = _state.requireValue().copy(emptyList = true)
    }

    private fun apiFail() {
        _state.value = _state.requireValue().copy(apiFail = true)
    }

    data class State(
        val getPostInProgress: Boolean = false,
        val emptyList: Boolean = false,
        val apiFail: Boolean = false

    ) {
        val showProgress: Boolean get() = getPostInProgress
        val enableViews: Boolean get() = !getPostInProgress
        val emptyListInfo: Boolean get() = emptyList
        val apiFailInfo: Boolean get() = apiFail
    }
}