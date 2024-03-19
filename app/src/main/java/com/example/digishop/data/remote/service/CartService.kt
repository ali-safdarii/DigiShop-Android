package com.example.digishop.data.remote.service

import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.util.NoDataResponse
import com.example.digishop.data.remote.responses.cart.response.CartResponseApi
import com.example.digishop.data.remote.responses.cart.response.ItemCountResponse
import com.example.digishop.data.remote.responses.cart.response.ProductExist
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface CartService {
    @GET("api/carts/items")
    suspend fun cartItem(
        @Header("Authorization") token: String
    ): BaseResponse<CartResponseApi>

    @POST("api/carts/exsit-product")
    @FormUrlEncoded
    suspend fun existProduct(
        @Field("product_id") productId: Int,
        @Field("color_id") colorId: Int,
        @Header("Authorization") token: String
    ): BaseResponse<ProductExist>

    @GET("api/carts/items-count")
    suspend fun getItemsCount(
        @Header("Authorization") token: String
    ): BaseResponse<ItemCountResponse>

    @POST("api/carts/create")
    @FormUrlEncoded
    suspend fun insert(
        @Field("product_id") productId: Int,
        @Field("color_id") colorId: Int,
        @Field("final_price") finalPrice: Int,
        @Field("qty") qty: Int,
        @Header("Authorization") token: String
    ): NoDataResponse

    @POST("api/carts/delete-item")
    @FormUrlEncoded
    suspend fun delete(
        @Field("product_id") productId: Int,
        @Field("color_id") colorId: Int,
        @Header("Authorization") token: String
    ): NoDataResponse


    @PATCH("api/carts/update-item")
    @FormUrlEncoded
    suspend fun update(
        @Field("product_id") productId: Int,
        @Field("color_id") colorId: Int,
        @Field("qty") quantity: Int,
        @Field("final_price") finalPrice: Int,
        @Header("Authorization") token: String
    ): NoDataResponse



}