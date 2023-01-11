package com.example.recyclev

import com.example.recyclev.model.Post

interface Navigator {

    fun showDetails(post: Post)

    fun goBack()

    fun toast(messageRes: Int)

}