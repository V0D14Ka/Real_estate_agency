package com.example.recyclev

import android.app.Application
import com.example.recyclev.model.PostService

class App : Application() {
    val postsService = PostService()
}