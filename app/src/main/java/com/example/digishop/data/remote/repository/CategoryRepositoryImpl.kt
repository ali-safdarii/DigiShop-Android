package com.example.digishop.data.remote.repository


import com.example.digishop.data.remote.datasource.CategoryRemoteDataSource
import com.example.digishop.data.remote.responses.product_response.CategoryResponse
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.category.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {


    override suspend fun categories(): BaseResponse<CategoryResponse> =
        remoteDataSource.categories()

    override suspend fun productsByCategoryId(catId: Int): BaseResponse<ProductResponse> =
        remoteDataSource.productsByCategoryId(categoryId = catId)

}