package com.example.digishop.data.remote.repository

import com.example.digishop.data.remote.datasource.AuthRemoteDataSource
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun loginOrRegister(loginRegisterRequest: LoginRegisterRequest) =
        remoteDataSource.loginOrRegister(loginRegisterRequest)

}