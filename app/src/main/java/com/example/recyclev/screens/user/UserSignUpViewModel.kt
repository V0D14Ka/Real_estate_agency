package com.example.recyclev.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclev.*
import com.example.recyclev.Singletons.usersRepository
import com.example.recyclev.model.user.Field
import com.example.recyclev.model.user.SignUpData
import com.example.recyclev.model.user.UsersRepository
import com.example.recyclev.utils.MutableLiveEvent
import com.example.recyclev.utils.MutableUnitLiveEvent
import com.example.recyclev.utils.publishEvent
import com.example.recyclev.utils.share
import kotlinx.coroutines.launch

class SignUpViewModel(
    usersRepository: UsersRepository = Singletons.usersRepository,
) : ViewModel() {

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _showToastEvent = MutableLiveEvent<Int>()
    val showToastEvent = _showToastEvent.share()

    private val _showExistsErrorEvent = MutableLiveEvent<Int>()
    val showExistsEvent = _showExistsErrorEvent.share()

    private val _showEmailErrorEvent = MutableLiveEvent<Int>()
    val showEmailEvent = _showEmailErrorEvent.share()

    private val _showPasswordErrorEvent = MutableLiveEvent<Int>()
    val showPasswordEvent = _showPasswordErrorEvent.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    fun signUp(signUpData: SignUpData) = viewModelScope.launch {
        showProgress()
        try {
            usersRepository.signUp(signUpData)
            showSuccessSignUpMessage()
            goBack()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: PasswordMismatchException) {
            processPasswordMismatchException()
        } catch (e: AccountAlreadyExistsException) {
            processAccountAlreadyExistsException()
        } catch (e: IncorrectEmailException) {
            processIncorrectEmailException()
        } catch (e: IncorrectPasswordException) {
            processIncorrectPasswordException()
        }
        finally {
            hideProgress()
        }
    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyUsernameError = e.field == Field.Username,
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password,
            emptyRepeatPasswordError = e.field == Field.Password,
            emptyPhoneError = e.field == Field.Password
        )
    }


    private fun processPasswordMismatchException() {
        _state.value = _state.requireValue().copy(
            passwordMismatchError = true
        )
    }

    private fun processIncorrectEmailException() {
        showEmailError()
    }

    private fun processIncorrectPasswordException() {
        showPasswordError()
    }

    private fun processAccountAlreadyExistsException() {
        showExistsError()
    }

    private fun showProgress() {
        _state.value = State(signUpInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    private fun showSuccessSignUpMessage() = _showToastEvent.publishEvent(R.string.sign_up_success)

    private fun showExistsError() = _showExistsErrorEvent.publishEvent(R.string.account_already_exists)

    private fun showEmailError() = _showEmailErrorEvent.publishEvent(R.string.incorrect_email)

    private fun showPasswordError() = _showPasswordErrorEvent.publishEvent(R.string.incorrect_password)

    private fun goBack() = _goBackEvent.publishEvent()

    data class State(
        val emptyUsernameError: Boolean = false,
        val emptyEmailError: Boolean = false,
        val emptyPhoneError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val emptyRepeatPasswordError: Boolean = false,
        val passwordMismatchError: Boolean = false,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }
}