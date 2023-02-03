package com.example.recyclev.model.user

import com.example.recyclev.*
import com.example.recyclev.model.settings.AppSettings
import com.example.recyclev.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import com.example.recyclev.model.*


class UsersRepository(
    private val usersSource: UsersSource,
    private val appSettings: AppSettings
) {

    private val accountLazyFlowSubject = LazyFlowSubject<Unit, User> {
        doGetAccount()
    }

    /**
     * Whether user is signed-in or not.
     */
    fun isSignedIn(): Boolean {
        // user is signed-in if auth token exists
        return appSettings.getCurrentToken() != null
    }

    /**
     * Try to sign-in with the email and password.
     * @throws EmptyFieldException
     * @throws InvalidCredentialsException
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun signIn(username: String, password: String) {
        if (username.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)

        val token = try {
            usersSource.signIn(username, password)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            }
            if (e is BackendException && e.code == 400) {
                throw InvalidInputException(e)
            } else {
                throw e
            }
        }
        val tok = "Token $token"
        // success! got auth token -> save it
        appSettings.setCurrentToken(tok)
        // and load account data
        accountLazyFlowSubject.updateAllValues(usersSource.getUser())
    }

    /**
     * Create a new account.
     * @throws EmptyFieldException
     * @throws PasswordMismatchException
     * @throws AccountAlreadyExistsException
     * @throws ConnectionException
     * @throws BackendException
     */
    suspend fun signUp(signUpData: SignUpData) {
        signUpData.validate()
        val id = try {
            usersSource.signUp(signUpData)
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 400 ) {
                if (e.message == "Пользователь с таким именем уже существует.")
                    throw AccountAlreadyExistsException(e)
                else if (e.message == "Введите правильный адрес электронной почты.")
                    throw IncorrectEmailException(e)
                else
                    throw IncorrectPasswordException(e)
            }
            else throw e
        }
        appSettings.setCurrentId(id)
    }


    /**
     * Reload account info. Results of reloading are delivered to the flows
     * returned by [getAccount] method.
     */
    fun reloadAccount() {
        accountLazyFlowSubject.reloadAll()
    }

    /**
     * Get the account info of the current signed-in user and listen for all
     * further changes of the account data.
     * If user is not logged-in an empty result is emitted.
     * @return infinite flow, always success; errors are wrapped to [Result]
     */
    fun getAccount(): Flow<Result<User>> {
        return accountLazyFlowSubject.listen(Unit)
    }

    /**
     * Change the username of the current signed-in user. An updated account
     * entity is delivered to the flow returned by [getAccount] call after
     * calling this method.
     * @throws EmptyFieldException
     * @throws AuthException
     * @throws ConnectionException
     * @throws BackendException
     * @throws ParseBackendResponseException
     */
    suspend fun updateAccountUsername(newUsername: String) = wrapBackendExceptions {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
        usersSource.setUsername(newUsername)
        accountLazyFlowSubject.updateAllValues(usersSource.getUser())
    }

    /**
     * Clear JWT-token saved in settings.
     */
    fun logout() {
        appSettings.setCurrentToken(null)
        accountLazyFlowSubject.updateAllValues(null)
    }

    private suspend fun doGetAccount(): User = wrapBackendExceptions {
        try {
            usersSource.getUser()
        } catch (e: BackendException) {
            // account has been deleted = session expired = AuthException
            if (e.code == 404) throw AuthException(e)
            else throw e
        }
    }

}