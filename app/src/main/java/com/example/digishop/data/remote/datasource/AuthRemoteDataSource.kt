package com.example.digishop.data.remote.datasource

import com.example.digishop.data.remote.responses.auth.AuthResponse
import com.example.digishop.data.remote.service.AuthService
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.di.IoDispatcher
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface AuthRemoteDataSource {
    suspend fun loginOrRegister(
        loginRegisterRequest: LoginRegisterRequest
    ): BaseResponse<AuthResponse>
}

class AuthRemoteImpl @Inject constructor(
    private val authService: AuthService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRemoteDataSource {
    override suspend fun loginOrRegister(loginRegisterRequest: LoginRegisterRequest): BaseResponse<AuthResponse> =
        withContext(ioDispatcher) {
            authService.loginOrRegister(loginRegisterRequest)
        }

}