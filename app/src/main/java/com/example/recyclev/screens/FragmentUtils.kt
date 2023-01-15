package com.example.recyclev.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclev.Singletons
import com.example.recyclev.screens.main.Navigator
import com.example.recyclev.viewmodel.PostsDetailsViewModel
import com.example.recyclev.viewmodel.PostsListViewModel
import com.example.recyclev.viewmodel.SignInViewModel
import com.example.recyclev.viewmodel.SignUpViewModel


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
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(Singletons)

fun Fragment.navigator() = requireActivity() as Navigator
