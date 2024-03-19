package com.example.digishop.data.remote.datasource

import com.example.digishop.data.remote.responses.product_response.CategoryResponse
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.data.remote.service.CategoryService
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface CategoryRemoteDataSource {

    suspend fun categories(): BaseResponse<CategoryResponse>
    suspend fun productsByCategoryId(categoryId: Int): BaseResponse<ProductResponse>
}

class CategoryRemoteImpl @Inject constructor(
    private val categoryService: CategoryService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CategoryRemoteDataSource {
    override suspend fun categories(): BaseResponse<CategoryResponse> =
        withContext(ioDispatcher) {
            categoryService.getCategories()
        }

    override suspend fun productsByCategoryId(categoryId: Int): BaseResponse<ProductResponse> =
        withContext(ioDispatcher) {
            categoryService.getProductsByCategoryId(categoryId)
        }


}