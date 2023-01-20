package com.example.recyclev.model.user

import com.example.recyclev.EmptyFieldException
import com.example.recyclev.PasswordMismatchException

/**
 * Fields that should be provided during creating a new account.
 */
data class SignUpData(
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val repeatPassword: String,
) {

    /**
     * @throws EmptyFieldException
     * @throws PasswordMismatchException
     */
    fun validate() {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (username.isBlank()) throw EmptyFieldException(Field.Username)
        if (phone.isBlank()) throw EmptyFieldException(Field.Phone)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        if (password != repeatPassword) throw PasswordMismatchException()
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
    }
}