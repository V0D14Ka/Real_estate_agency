package com.example.recyclev.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.model.user.UsersRepository
import com.example.recyclev.utils.MutableLiveEvent
import com.example.recyclev.utils.publishEvent
import com.example.recyclev.utils.share
import kotlinx.coroutines.launch

/**
 * SplashViewModel checks whether user is signed-in or not.
 */
class SplashViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _launchMainScreenEvent = MutableLiveEvent<Boolean>()
    val launchMainScreenEvent = _launchMainScreenEvent.share()

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.publishEvent(usersRepository.isSignedIn())
        }
    }
}