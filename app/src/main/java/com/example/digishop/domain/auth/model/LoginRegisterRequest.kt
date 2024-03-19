package com.example.digishop.domain.auth.model

@JvmInline
value class Email(val value: String)

@JvmInline
value class Password(val value: String)


data class LoginRegisterRequest (
    val email: Email = Email(""),
    val password: Password = Password("")
)

