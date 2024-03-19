package com.example.digishop.domain.session

import com.example.digishop.domain.token.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * A use case to extract the domain logic to check if a user is currently signed in
 * on this device.
 */
class IsUserLoggedInUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
) {

    fun isUserLoggedIn(): Flow<Boolean> {
        return tokenRepository
            .observeToken()
            .map { token ->
                token != null
            }
    }
}
