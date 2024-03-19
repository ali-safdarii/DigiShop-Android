package com.example.digishop.domain.auth.repository

import com.example.digishop.data.remote.responses.auth.AuthResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.auth.model.LoginRegisterRequest

interface AuthRepository {
    suspend fun loginOrRegister(loginRegisterRequest: LoginRegisterRequest): BaseResponse<AuthResponse>
}