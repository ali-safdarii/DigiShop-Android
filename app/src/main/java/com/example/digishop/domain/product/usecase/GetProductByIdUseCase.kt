package com.example.digishop.domain.product.usecase

import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.product.model.Product
import com.example.digishop.domain.product.repository.ProductRepository
import com.example.digishop.data.remote.responses.product_response.toProduct
import com.example.digishop.domain.error.CustomErrorType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend fun invoke(pathId: Int): Flow<AsyncResult<Product>> = flow {

        val result = repository.productById(pathId)

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val product = result.data.toProduct()

        emit(AsyncResult.Success(product))

    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }

}