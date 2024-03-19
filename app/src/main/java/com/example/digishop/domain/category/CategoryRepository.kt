package com.example.digishop.domain.category

import com.example.digishop.data.remote.responses.product_response.CategoryResponse
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.data.remote.util.BaseResponse

interface CategoryRepository {

    suspend fun categories(): BaseResponse<CategoryResponse>

    suspend fun productsByCategoryId(catId:Int): BaseResponse<ProductResponse>
}