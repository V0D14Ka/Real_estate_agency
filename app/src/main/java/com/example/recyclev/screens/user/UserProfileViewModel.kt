package com.example.recyclev.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.model.user.User
import com.example.recyclev.model.user.UsersRepository
import com.example.recyclev.utils.MutableLiveEvent
import com.example.recyclev.utils.publishEvent
import com.example.recyclev.utils.share
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _account = MutableLiveData<User>()
    val account = _account.share()

    private val _restartFromLoginEvent = MutableLiveEvent<Unit>()
    val restartWithSignInEvent = _restartFromLoginEvent.share()

    init {
        viewModelScope.launch {
            usersRepository.getAccount().collect {
                _account.value = it.getValueOrNull()
            }
        }
    }

    fun logout() {
        usersRepository.logout()
        restartAppFromLoginScreen()
    }

    private fun restartAppFromLoginScreen() {
        _restartFromLoginEvent.publishEvent()
    }

}