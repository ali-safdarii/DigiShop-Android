package com.example.digishop.data.remote.repository

import com.example.digishop.data.remote.datasource.CartRemoteDataSource
import com.example.digishop.data.remote.responses.cart.response.CartResponseApi
import com.example.digishop.data.remote.responses.cart.response.ItemCountResponse
import com.example.digishop.data.remote.responses.cart.response.ProductExist
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.util.NoDataResponse
import com.example.digishop.domain.cart.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartRemoteDataSource,
) : CartRepository {
    override suspend fun cartItems(token: String): BaseResponse<CartResponseApi> =
        cartDataSource.cartItems(token = token)

    override suspend fun insert(productId: Int, colorId: Int,finalPrice:Int,qty: Int, token: String): NoDataResponse =
        cartDataSource.insert(productId=productId, colorId = colorId, finalPrice = finalPrice,qty=qty, token=token)

    override suspend fun removeFromCart(
        productId: Int,
        colorId: Int,
        token: String
    ): NoDataResponse = cartDataSource.delete(productId, colorId, token)

    override suspend fun update(
        productId: Int,
        colorId: Int,
        quantity: Int,
        finalPrice: Int,
        token: String
    ): NoDataResponse = cartDataSource.update(
        productId = productId,
        colorId = colorId,
        quantity = quantity,
        finalPrice = finalPrice,
        token = token
    )

    override suspend fun getcartItemsCount(token: String): BaseResponse<ItemCountResponse> =
        cartDataSource.getItemsCount(token = token)


    override suspend fun isProductInCart(
        productId: Int,
        colorId: Int,
        token: String
    ): BaseResponse<ProductExist> =
        cartDataSource.existProduct(productId = productId, colorId = colorId, token = token)


}