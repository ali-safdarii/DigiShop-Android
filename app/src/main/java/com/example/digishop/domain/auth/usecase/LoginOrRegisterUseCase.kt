package com.example.digishop.domain.auth.usecase

import com.example.digishop.data.remote.responses.auth.toUser
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.auth.model.LoginRegisterRequest
import com.example.digishop.domain.auth.model.ValidationType
import com.example.digishop.domain.auth.repository.AuthRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.token.model.JwtToken
import com.example.digishop.domain.token.model.Token
import com.example.digishop.domain.token.repository.TokenRepository
import com.example.digishop.domain.user.model.User
import timber.log.Timber
import javax.inject.Inject


class LoginOrRegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val validation: ValidationInputsUseCase
) {

    suspend fun invoke(loginRegisterRequest: LoginRegisterRequest): AsyncResult<User> {

        try {

            if (validation.invoke(loginRegisterRequest) != ValidationType.Valid)
                return AsyncResult.Failure(CustomErrorType.CustomMessage(message = "Validation Input is Failed ...."))


            val repoResult = repository.loginOrRegister(loginRegisterRequest)

            if (!repoResult.success)
                return AsyncResult.Failure(CustomErrorType.CustomMessage(message = repoResult.message))


            val user = repoResult.data.toUser()

            val tokenResult = tokenRepository.storeToken(Token(jwt = JwtToken(user.jwtToken)))


            Timber.d(repoResult.message)
            return AsyncResult.Success(user)

        } catch (e: Throwable) {
            return AsyncResult.Failure(getError(e))
        }


    }


}
