package com.example.digishop.domain.cart.repository

import com.example.digishop.data.remote.responses.cart.response.CartResponseApi
import com.example.digishop.data.remote.responses.cart.response.ItemCountResponse
import com.example.digishop.data.remote.responses.cart.response.ProductExist
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.util.NoDataResponse

interface CartRepository {

    suspend fun cartItems(token: String): BaseResponse<CartResponseApi>
    suspend fun isProductInCart(
        productId: Int,
        colorId: Int,
        token: String
    ): BaseResponse<ProductExist>

    suspend fun insert(productId: Int, colorId: Int, finalPrice: Int,qty: Int, token: String): NoDataResponse

    suspend fun removeFromCart(productId: Int, colorId: Int, token: String): NoDataResponse

    suspend fun update(
        productId: Int,
        colorId: Int,
        quantity: Int,
        finalPrice: Int,
        token: String
    ): NoDataResponse

    suspend fun getcartItemsCount(token: String): BaseResponse<ItemCountResponse>
}