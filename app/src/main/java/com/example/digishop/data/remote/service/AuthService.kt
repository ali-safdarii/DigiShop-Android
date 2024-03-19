package com.example.digishop.data.remote.service

import com.example.digishop.data.remote.responses.auth.AuthResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/loginOrRegister")
    suspend fun loginOrRegister(
        @Body loginRegisterRequest: LoginRegisterRequest
    ): BaseResponse<AuthResponse>

}