package com.example.recyclev.model.post

import com.example.recyclev.model.user.SignUpData
import com.example.recyclev.model.user.User

interface PostsSource {

//    suspend fun signIn(email: String, password: String): String

    /**
     * Create a new account.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
//    suspend fun signUp(signUpData: SignUpData)

    /**
     * Get the account info of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun getPosts(): List<Post>

    suspend fun getPost(id: Char): Post

    suspend fun likePost(id: Long) : Post
    /**
     * Change the username of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
//    suspend fun setUsername(username: String)
}