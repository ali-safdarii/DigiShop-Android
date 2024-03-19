package com.example.digishop.domain.token.usecase

import com.example.digishop.domain.token.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject


class BearerTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {

   fun bearerToken(): Flow<String?> {
        return tokenRepository.observeToken().map { token ->
            Timber.d("Token :Bearer $token")
            "Bearer ${token?.jwt?.value}"
        }
    }
}
