package com.example.digishop.domain.cart.usecase

import com.example.digishop.data.remote.util.NoDataResponse
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.cart.repository.CartRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.session.IsUserLoggedInUseCase
import com.example.digishop.domain.token.usecase.BearerTokenUseCase
import com.example.digishop.utils.Constants
import com.example.digishop.utils.Constants.AUTH_ERROR_MESSAGE
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val bearerTokenUseCase: BearerTokenUseCase
) {

    suspend fun invoke(productId: Int, colorId: Int): AsyncResult<NoDataResponse> {
        try {
            val isLoggedIn = isUserLoggedInUseCase.isUserLoggedIn().first()

            if (!isLoggedIn) {
                return AsyncResult.Failure(CustomErrorType.CustomMessage(message = AUTH_ERROR_MESSAGE))
            }

            val token = bearerTokenUseCase.bearerToken().first() ?: Constants.JWT_ERROR_MESSAGE

            val result =
                cartRepository.removeFromCart(productId = productId, colorId = colorId, token = token)


            if (!result.success) {
                return AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message))
            }

            return AsyncResult.Success(result)
        } catch (e: Exception) {
            return AsyncResult.Failure(getError(e))
        }
    }

}