package com.example.recyclev.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.recyclev.R
import com.example.recyclev.Singletons
import com.example.recyclev.screens.main.SplashViewModel
import com.example.recyclev.screens.post.PostLikeViewModel
import com.example.recyclev.screens.post.PostsDetailsViewModel
import com.example.recyclev.screens.post.PostsListViewModel
import com.example.recyclev.screens.user.UserProfileViewModel
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
            UserProfileViewModel::class.java -> {
                UserProfileViewModel(singletons.usersRepository)
            }
            PostLikeViewModel::class.java -> {
                PostLikeViewModel(singletons.postRepository)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(Singletons)

fun Fragment.findTopNavController() : NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}
