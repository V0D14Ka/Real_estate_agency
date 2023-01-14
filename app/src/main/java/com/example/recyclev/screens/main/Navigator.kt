package com.example.recyclev.screens.main

import com.example.recyclev.model.post.Post

interface Navigator {

    fun showDetails(post: Post)

    fun goBack()

    fun toast(messageRes: Int)

    fun onSignedIn()

    fun onSignedUp()

    fun signUp()

}