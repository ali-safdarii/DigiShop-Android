package com.example.digishop.domain.auth.model

import com.example.digishop.domain.token.model.Token

data class AuthSuccess(
    val token: Token,
)