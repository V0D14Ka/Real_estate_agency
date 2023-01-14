package com.example.recyclev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.*
import com.example.recyclev.Singletons.usersRepository
import com.example.recyclev.model.user.Field
import com.example.recyclev.model.user.UsersRepository
import com.example.recyclev.utils.MutableLiveEvent
import com.example.recyclev.utils.MutableUnitLiveEvent
import com.example.recyclev.utils.publishEvent
import com.example.recyclev.utils.share
import kotlinx.coroutines.launch

class SignInViewModel(
    usersRepository: UsersRepository = Singletons.usersRepository,
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _clearPasswordEvent = MutableUnitLiveEvent()
    val clearPasswordEvent = _clearPasswordEvent.share()

    private val _showAuthErrorToastEvent = MutableLiveEvent<Int>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    private val _onNextPageEvent = MutableUnitLiveEvent()
    val onNextPageEvent = _onNextPageEvent.share()

    private val _signUpEvent = MutableUnitLiveEvent()
    val signUpEvent = _signUpEvent.share()

//    private val _navigateToTabsEvent = MutableUnitLiveEvent()
//    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun signIn(username: String, password: String) = viewModelScope.launch {
        showProgress()
        try {
            usersRepository.signIn(username, password)
            processOK()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: InvalidCredentialsException) {
            processInvalidCredentialsException()
        } catch (e: InvalidInputException){
            processInvalidInputException()
        }
        finally {
            hideProgress()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password
        )
    }

    private fun processOK() {
        onNextPage()
    }


    private fun processInvalidCredentialsException() {
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun processInvalidInputException() {
        clearPasswordField()
        showAuthErrorToast()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signInInProgress = false)
    }

    private fun clearPasswordField() = _clearPasswordEvent.publishEvent()

    private fun showAuthErrorToast() = _showAuthErrorToastEvent.publishEvent(R.string.invalid_email_or_password)

    private fun onNextPage() = _onNextPageEvent.publishEvent()

    private fun signUp() = _signUpEvent.publishEvent()


    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }
}