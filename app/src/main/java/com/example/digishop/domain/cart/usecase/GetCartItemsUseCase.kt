package com.example.digishop.domain.cart.usecase

import com.example.digishop.data.remote.responses.cart.response.toCartItems
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.cart.model.CartItem
import com.example.digishop.domain.cart.model.CartSummery
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
import timber.log.Timber
import javax.inject.Inject

data class CartListResult(
    val cartItem: List<CartItem>,
    val cartSummery: CartSummery,
)

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val bearerTokenUseCase: BearerTokenUseCase
) {
    suspend fun invoke(): Flow<AsyncResult<CartListResult>> = flow {

        val isLoggedIn = isUserLoggedInUseCase.isUserLoggedIn().first()

        if (!isLoggedIn) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = AUTH_ERROR_MESSAGE)))
            return@flow
        }

        val token =
            bearerTokenUseCase.bearerToken().first() ?: throw Exception(Constants.JWT_ERROR_MESSAGE)

        val result = cartRepository.cartItems(token)
        val cartResponse = result.data

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val cartItems = cartResponse.toCartItems()

        cartItems.forEach {
            Timber.d(it.toString())
        }


        val cartSummery = calculateTotalPrice(cartItems)

        CartListResult(
            cartSummery = cartSummery,
            cartItem = cartItems
        ).let {
            emit(AsyncResult.Success(it))
        }

    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }



    fun calculateTotalPrice(cartItems: List<CartItem>): CartSummery {
        val totalCount = cartItems.size
        val priceWithoutDiscount = cartItems.sumOf { it.unitPrice * it.quantity }
        val totalDiscount = cartItems.sumOf { it.quantity * it.productDiscount }

        /*
                val discountPercentage =
                    (totalDiscount.toDouble() / priceWithoutDiscount.toDouble()) * 100*/


        val overallTotal = priceWithoutDiscount - totalDiscount

        return CartSummery(
            itemCount = totalCount,
            totalPriceWithoutDiscount = priceWithoutDiscount,
            totalDiscount = totalDiscount,
            total = overallTotal,
        )
    }
}