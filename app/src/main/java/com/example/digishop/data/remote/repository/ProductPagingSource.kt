package com.example.digishop.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.digishop.data.remote.service.ProductService
import com.example.digishop.data.remote.responses.product_response.toProduct
import com.example.digishop.domain.product.model.Product
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class ProductPagingSource(
    private val productService: ProductService
) : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {



        return try {
            val currentPage = params.key ?: 1
            Timber.tag("paging3").d("currentPage: ${params.key}, loadsize: ${params.loadSize}")

            val response = productService.getProducts(currentPage)
            val products = response.productData.map { it.toProduct() }

            LoadResult.Page(
                data = products,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if(response.meta.lastPage == currentPage) null else currentPage + 1
            )
        } catch (exception: IOException) {
            Timber.e(exception.localizedMessage)
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.e(exception.localizedMessage)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
