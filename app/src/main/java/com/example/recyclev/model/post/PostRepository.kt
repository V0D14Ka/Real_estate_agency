package com.example.recyclev.model.post

import android.util.Log
import com.example.recyclev.model.user.User
import com.example.recyclev.model.user.UsersSource

typealias PostsListener = (posts: List<Post>) -> Unit

typealias LikedPostsListener = (likedposts: List<Post>) -> Unit

class PostRepository (
    private val postsSource: PostsSource,
    private val usersSource: UsersSource
) {

    private var posts = mutableListOf<Post>()

    private var likedposts = mutableListOf<Post>()

    private val listeners = mutableListOf<PostsListener>()

    private val likedlisteners = mutableListOf<LikedPostsListener>()


    suspend fun getPostsFromApi() {
        posts = postsSource.getPosts().toMutableList()
    }

    suspend fun getLikedPosts() {
        if (!posts.isNullOrEmpty())
            posts.forEach {
                if (it.is_favorite) likedposts.add(it)
            }
    }

    suspend fun LikePost(id: Long) : Boolean {
        var pp: Post? = null
        posts.forEach {
            if (it.id == id) {
                it.is_favorite = !it.is_favorite
                pp = it
            }
        }

        val index = likedposts.indexOfFirst { it.id == id }
        if(index != -1){
            likedposts.removeAt(index)
        }else likedposts.add(pp!!)

        notifyChanges()
        postsSource.likePost(id)
        return !likedposts.isEmpty()
    }

    fun getPosts(): List<Post> {
        return posts
    }

    fun getById(id: Long): Post {
        return posts.firstOrNull { it.id == id } ?: throw Exception("not found")
    }

    fun deletePost(post: Post){
        val index = posts.indexOfFirst { it.id == post.id }
        if(index != -1){
            posts.removeAt(index)
        }
        notifyChanges()
    }

    fun addListener(listener: PostsListener){
        listeners.add(listener)
        listener.invoke(posts)
    }

    fun addLikedListener(listener: LikedPostsListener){
        likedlisteners.add(listener)
        listener.invoke(likedposts)
    }

    fun removeListener(listener: PostsListener){
        listeners.remove(listener)
    }

    fun removeLikedListener(listener: LikedPostsListener){
        likedlisteners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(posts) }
        likedlisteners.forEach{ it.invoke(likedposts)}
    }
}