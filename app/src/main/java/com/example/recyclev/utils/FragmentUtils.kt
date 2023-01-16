package com.example.recyclev.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclev.Singletons
import com.example.recyclev.screens.main.SplashViewModel
import com.example.recyclev.screens.post.PostsDetailsViewModel
import com.example.recyclev.screens.post.PostsListViewModel
import com.example.recyclev.viewmodel.*


class ViewModelFactory(
    private val singletons: Singletons
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            PostsListViewModel::class.java -> {
                PostsListViewModel(singletons.postRepository)
            }
            PostsDetailsViewModel::class.java -> {
                PostsDetailsViewModel(singletons.postRepository)
            }
            SignInViewModel::class.java -> {
                SignInViewModel()
            }
            SignUpViewModel::class.java -> {
                SignUpViewModel()
            }
            SplashViewModel::class.java -> {
                SplashViewModel(singletons.usersRepository)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(Singletons)
