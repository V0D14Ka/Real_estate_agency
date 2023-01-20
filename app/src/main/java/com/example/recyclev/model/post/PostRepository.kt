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

//    init{
//        val faker = Faker.instance()
//        IMAGES.shuffle()
//        posts = (1..100).map {Post(
//            id = it.toLong(),
//            photo = IMAGES[it % IMAGES.size],
//            title = "Квартира",
//            town = faker.address().cityName(),
//            description = "Крутейшая хата с топовым видом.\nТоп за свои деньги.\nСкорее пишите и звоните!",
//            Price = PRICE[it % IMAGES.size].toLong(),
//        )}.toMutableList()
//    }

    suspend fun getPostsFromApi() {
        posts = postsSource.getPosts().toMutableList()
    }

    private suspend fun getPostFromApi(id: Char) {
        val p: Post = postsSource.getPost(id)
        likedposts.add(p)
    }


    suspend fun getLikedPosts() {
        val u : User = usersSource.getUser()
        if (u.favorite_adverts.isNotEmpty()) u.favorite_adverts.forEach { getPostFromApi(it[-1])}
        getPostFromApi('1')
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