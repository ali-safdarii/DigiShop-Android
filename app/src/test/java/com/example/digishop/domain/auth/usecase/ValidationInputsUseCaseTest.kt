package com.example.digishop.domain.auth.usecase

import com.example.digishop.domain.auth.model.Email
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.model.Password
import com.example.digishop.domain.auth.model.ValidationType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ValidationInputsUseCaseTest {

    private lateinit var validationInputsUseCase: ValidationInputsUseCase

    @Before
    fun setUp() {
        validationInputsUseCase = ValidationInputsUseCase()
    }



    @Test
    fun `empty email and password should return EmptyField`() {
        val request = LoginRegisterRequest(Email(""), Password(""))
        assertEquals(ValidationType.EmptyField, validationInputsUseCase(request))
    }


    @Test
    fun `empty email should return EmptyField`() {
        val request = LoginRegisterRequest(Email(""), Password("password"))
        assertEquals(ValidationType.EmptyField, validationInputsUseCase(request))
    }

    @Test
    fun `empty password should return EmptyField`() {
        val request = LoginRegisterRequest(Email("email@example.com"), Password(""))
        assertEquals(ValidationType.EmptyField, validationInputsUseCase(request))
    }


    @Test
    fun `password too short should return PasswordTooShort`() {
        val request = LoginRegisterRequest(Email("email@example.com"), Password("pass"))
        assertEquals(ValidationType.PasswordTooShort, validationInputsUseCase(request))
    }

    @Test
    fun `valid email and password should return Valid`() {
        val request = LoginRegisterRequest(Email("email@example.com"), Password("password"))
        assertEquals(ValidationType.Valid, validationInputsUseCase(request))
    }


    @Test
    fun test_invalid_email_missing_at_symbol() {
        val request = LoginRegisterRequest(Email("exampleexample.com"), Password("password"))
        assertEquals(ValidationType.InvalidEmail, validationInputsUseCase(request))
    }

    @Test
    fun test_invalid_email_missing_dotcom() {
        val request = LoginRegisterRequest(Email("example@domain"), Password("password"))
        assertEquals(ValidationType.InvalidEmail, validationInputsUseCase(request))
    }


    @Test
    fun test_invalid_email_missing_dot() {
        val request = LoginRegisterRequest(Email("example@examplecom"), Password("password"))
        assertEquals(ValidationType.InvalidEmail, validationInputsUseCase(request))
    }


    @Test
    fun test_invalid_email_too_short() {
        val request = LoginRegisterRequest(Email("e@example.c"), Password("password"))
        assertEquals(ValidationType.InvalidEmail, validationInputsUseCase(request))
    }




}