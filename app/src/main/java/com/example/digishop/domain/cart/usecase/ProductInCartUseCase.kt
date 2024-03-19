package com.example.digishop.domain.cart.usecase

import com.example.digishop.data.remote.responses.cart.response.ProductExist
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.cart.repository.CartRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.session.IsUserLoggedInUseCase
import com.example.digishop.domain.token.usecase.BearerTokenUseCase
import com.example.digishop.utils.Constants
import com.example.digishop.utils.Constants.AUTH_ERROR_MESSAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductInCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val bearerTokenUseCase: BearerTokenUseCase
) {
    suspend fun invoke(productId: Int, colorId: Int): Flow<AsyncResult<ProductExist>> = flow {

        val isLoggedIn = isUserLoggedInUseCase.isUserLoggedIn().first()

        if (!isLoggedIn) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = AUTH_ERROR_MESSAGE)))
            return@flow
        }

        val token = bearerTokenUseCase.bearerToken().first() ?: Constants.JWT_ERROR_MESSAGE

        val result =
            cartRepository.isProductInCart(productId = productId, colorId = colorId, token = token)


        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        emit(AsyncResult.Success(result.data))

    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }
}