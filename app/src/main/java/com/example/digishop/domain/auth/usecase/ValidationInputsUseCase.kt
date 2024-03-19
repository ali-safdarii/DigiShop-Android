package com.example.digishop.domain.auth.usecase

import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.model.ValidationType
import javax.inject.Inject

class ValidationInputsUseCase @Inject constructor() {


    operator fun invoke(request: LoginRegisterRequest): ValidationType {

        val emailField = request.email.value
        val passwordField = request.password.value

        if (emailField.isEmpty() || passwordField.isEmpty())
            return ValidationType.EmptyField


        if (!isValidEmail(emailField))
            return ValidationType.InvalidEmail

        if (passwordField.length < 5)
            return ValidationType.PasswordTooShort

        return ValidationType.Valid
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return emailRegex.matches(email)
    }
}


