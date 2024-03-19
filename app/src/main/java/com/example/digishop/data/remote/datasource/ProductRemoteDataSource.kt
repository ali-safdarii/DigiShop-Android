package com.example.digishop.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.digishop.data.remote.repository.ProductPagingSource
import com.example.digishop.data.remote.repository.ProductSearchPagingSource
import com.example.digishop.data.remote.responses.amazing_products.response.AmazingProductResponse
import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.service.ProductService
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.di.IoDispatcher
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.example.digishop.data.remote.responses.product_response.ProductResponse
import com.example.digishop.domain.product.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ProductRemoteDataSource {


    fun products(): Flow<PagingData<Product>>

    suspend fun productSearch(query: String): Flow<PagingData<Product>>

    suspend fun productById(pathId: Int): BaseResponse<ProductApiModel>

    suspend fun amazingProducts(): BaseResponse<AmazingProductResponse>
    suspend fun getSummeryProducts(): BaseResponse<ProductResponse>
    suspend fun banners(): BaseResponse<BannerResponse>


}

class ProductRemoteImpl @Inject constructor(
    private val productService: ProductService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher


) : ProductRemoteDataSource {

    override  fun products(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { ProductPagingSource(productService) }
        ).flow
    }

    override suspend fun productSearch(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 24,
                prefetchDistance = 12
            ),
            pagingSourceFactory = {
                ProductSearchPagingSource(productService, searchText = query)
            }
        ).flow
    }


    override suspend fun productById(pathId: Int): BaseResponse<ProductApiModel> =
        withContext(ioDispatcher) {
            productService.productById(id = pathId)
        }

    override suspend fun amazingProducts(): BaseResponse<AmazingProductResponse> =
        withContext(ioDispatcher) {
            productService.amazingProducts()
        }

    override suspend fun getSummeryProducts(): BaseResponse<ProductResponse> =
        withContext(ioDispatcher) {
            productService.getSummeryProducts()
        }

    override suspend fun banners(): BaseResponse<BannerResponse> = withContext(ioDispatcher) {
        productService.getBanners()
    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 5
    }

}

