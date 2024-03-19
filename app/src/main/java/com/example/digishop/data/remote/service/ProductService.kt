package com.example.digishop.data.remote.service

import com.example.digishop.data.remote.responses.amazing_products.response.AmazingProductResponse
import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("api/products")
    suspend fun getProducts(
        @Query("page") page: Int,
    ): ProductResponse

    @GET("api/search")
    suspend fun searchProducts(
        @Query("page") page: Int,
        @Query("query") searchText: String,
    ): ProductResponse
    @GET("api/products/{id}")
    suspend fun productById(
        @Path("id") id: Int
    ): BaseResponse<ProductApiModel>

    @GET("api/amazing-products")
    suspend fun amazingProducts(
    ): BaseResponse<AmazingProductResponse>


    @GET("api/products/summaries")
    suspend fun getSummeryProducts(
    ): BaseResponse<ProductResponse>

    @GET("api/banners")
    suspend fun getBanners(
    ): BaseResponse<BannerResponse>


}