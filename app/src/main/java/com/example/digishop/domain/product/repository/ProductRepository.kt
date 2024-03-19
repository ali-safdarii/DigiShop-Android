package com.example.digishop.domain.product.repository

import androidx.paging.PagingData
import com.example.digishop.data.remote.responses.amazing_products.response.AmazingProductResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.domain.product.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

     fun productPagingData(): Flow<PagingData<Product>>
    suspend fun searchProduct(searchText: String): Flow<PagingData<Product>>
    suspend fun productById(pathId: Int): BaseResponse<ProductApiModel>
    suspend fun amazingProducts(): BaseResponse<AmazingProductResponse>

    suspend fun getSummeryProducts(): BaseResponse<ProductResponse>

}