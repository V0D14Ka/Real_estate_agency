package com.example.recyclev.model

import com.github.javafaker.Faker
import java.util.Collections

typealias PostsListener = (posts: List<Post>) -> Unit

class PostService {

    private var posts = mutableListOf<Post>()

    private val listeners = mutableListOf<PostsListener>()

    init{
        val faker = Faker.instance()
        IMAGES.shuffle()
        posts = (1..100).map {Post(
            id = it.toLong(),
            photo = IMAGES[it % IMAGES.size],
            title = "Квартира",
            description = faker.address().cityName(),
            Price = PRICE[it % IMAGES.size].toLong(),
        )}.toMutableList()
    }

    fun getPosts(): List<Post>{
        return posts
    }

    fun deletePost(post: Post){
        val index = posts.indexOfFirst { it.id == post.id }
        if(index != -1){
            posts.removeAt(index)
        }
        notifyChanges()
    }

    fun movePost(post: Post, moveBy: Int){
        val oldIndex = posts.indexOfFirst { it.id == post.id }
        if(oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if(newIndex < 0 || newIndex >= posts.size) return
        Collections.swap(posts, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: PostsListener){
        listeners.add(listener)
        listener.invoke(posts)
    }

    fun removeListener(listener: PostsListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(posts) }
    }
    companion object{
        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1518780664697-55e3ad937233?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=765&q=80",
            "https://images.unsplash.com/photo-1568605114967-8130f3a36994?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1570129477492-45c003edd2be?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
            "https://images.unsplash.com/photo-1575517111478-7f6afd0973db?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
        )
        private val PRICE = mutableListOf(
            12000,
            8999,
            15000,
            24000,
            6500,
            99999,
            9500,
            18700,
        )
    }
}