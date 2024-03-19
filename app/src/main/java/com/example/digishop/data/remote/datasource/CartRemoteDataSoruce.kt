package com.example.digishop.data.remote.datasource

import com.example.digishop.data.remote.service.CartService
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.util.NoDataResponse
import com.example.digishop.data.remote.responses.cart.response.CartResponseApi
import com.example.digishop.data.remote.responses.cart.response.ItemCountResponse
import com.example.digishop.data.remote.responses.cart.response.ProductExist
import com.example.digishop.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface CartRemoteDataSource {
    suspend fun insert(
        productId: Int,
        colorId: Int,
        finalPrice: Int,
        qty: Int,
        token: String
    ): NoDataResponse

    suspend fun delete(
        productId: Int,
        colorId: Int,
        token: String
    ): NoDataResponse

    suspend fun existProduct(
        productId: Int,
        colorId: Int,
        token: String
    ): BaseResponse<ProductExist>


    suspend fun getItemsCount(token: String): BaseResponse<ItemCountResponse>

    suspend fun update(
        productId: Int,
        colorId: Int,
        quantity: Int,
        finalPrice: Int,
        token: String
    ): NoDataResponse


    suspend fun cartItems(token: String): BaseResponse<CartResponseApi>

}

class CartRemoteImpl @Inject constructor(
    private val cartService: CartService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher


) : CartRemoteDataSource {
    override suspend fun insert(
        productId: Int,
        colorId: Int,
        finalPrice: Int,
        qty: Int,
        token: String
    ): NoDataResponse = withContext(ioDispatcher) {
        cartService.insert(
            productId = productId,
            colorId = colorId,
            finalPrice = finalPrice,
            qty = qty,

            token = token
        )
    }


    override suspend fun delete(
        productId: Int,
        colorId: Int,
        token: String
    ): NoDataResponse = withContext(ioDispatcher) {
        cartService.delete(productId, colorId, token)
    }

    override suspend fun existProduct(
        productId: Int,
        colorId: Int,
        token: String
    ): BaseResponse<ProductExist> = withContext(ioDispatcher) {
        cartService.existProduct(productId = productId, colorId = colorId, token = token)
    }

    override suspend fun getItemsCount(token: String): BaseResponse<ItemCountResponse> =
        withContext(ioDispatcher) {
            cartService.getItemsCount(token)
        }

    override suspend fun update(
        productId: Int,
        colorId: Int,
        quantity: Int,
        finalPrice: Int,
        token: String
    ): NoDataResponse = withContext(ioDispatcher) {
        cartService.update(
            productId = productId,
            colorId = colorId,
            quantity = quantity,
            finalPrice = finalPrice,
            token = token
        )
    }


    override suspend fun cartItems(token: String): BaseResponse<CartResponseApi> =
        withContext(ioDispatcher) {
            cartService.cartItem(token)
        }


    /*    override suspend fun insert(productId: Int, colorId: Int, token: String): NoDataResponse =    cartService.insert(productId, colorId, token)

        override suspend fun delete(productId: Int, colorId: Int, token: String): NoDataResponse =   cartService.delete(productId, colorId, token)

        override suspend fun existProduct(
            productId: Int,
            colorId: Int,
            token: String
        ): BaseResponse<ProductExist> =    cartService.existProduct(productId, colorId, token)

        override suspend fun getItemsCount(token: String): BaseResponse<ItemCountResponse> =   cartService.getItemsCount(token)

        override suspend fun updateQty(
            productId: Int,
            colorId: Int,
            quantity: Int,
            token: String
        ): NoDataResponse  =   cartService.updateItem(
            productId = productId,
            colorId = colorId,
            quantity = quantity,
            token = token
        )

        override suspend fun cartItems(token: String): BaseResponse<CartItemResponse> =   cartService.cartItem(token)*/
}