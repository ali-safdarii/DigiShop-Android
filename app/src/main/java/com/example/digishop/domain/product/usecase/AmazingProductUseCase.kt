package com.example.digishop.domain.product.usecase

import com.example.digishop.data.remote.responses.amazing_products.response.AmazingData
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AmazingProductUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend fun invoke() :Flow<AsyncResult<List<AmazingData>>>  = flow {


        val  result = repository.amazingProducts()

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val product = result.data.amazingDataList


        emit(AsyncResult.Success(product))

    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }
}