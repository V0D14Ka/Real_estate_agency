package com.example.recyclev.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclev.App
import com.example.recyclev.Navigator
import com.example.recyclev.viewmodel.PostsDetailsViewModel
import com.example.recyclev.viewmodel.PostsListViewModel


class ViewModelFactory(
    private val app: App
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            PostsListViewModel::class.java -> {
                PostsListViewModel(app.postsService)
            }
            PostsDetailsViewModel::class.java -> {
                PostsDetailsViewModel(app.postsService)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator
