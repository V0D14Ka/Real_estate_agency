package com.example.recyclev.screens.main

import android.app.Application
import com.example.recyclev.model.post.PostService

class App : Application() {
    val postsService = PostService()
}