package com.example.digishop.data.remote.repository

import androidx.paging.PagingData
import com.example.digishop.data.remote.datasource.ProductRemoteDataSource
import com.example.digishop.data.remote.responses.amazing_products.response.AmazingProductResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.product.repository.ProductRepository
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.domain.product.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,

    ) : ProductRepository {

    override  fun productPagingData(): Flow<PagingData<Product>> =
        remoteDataSource.products()

    override suspend fun searchProduct(searchText: String): Flow<PagingData<Product>> =
        remoteDataSource.productSearch(query = searchText)

    override suspend fun productById(pathId: Int): BaseResponse<ProductApiModel> =
        remoteDataSource.productById(pathId = pathId)

    override suspend fun amazingProducts(): BaseResponse<AmazingProductResponse> =
        remoteDataSource.amazingProducts()

    override suspend fun getSummeryProducts(): BaseResponse<ProductResponse> =
        remoteDataSource.getSummeryProducts()


}

