package com.example.digishop.data.remote.service

import com.example.digishop.data.remote.responses.product_response.CategoryResponse
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.data.remote.util.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    @GET("api/categories")
    suspend fun getCategories(
    ): BaseResponse<CategoryResponse>

    @GET("/api/categories/{id}/products")
    suspend fun getProductsByCategoryId(@Path("id") categoryId: Int): BaseResponse<ProductResponse>
}