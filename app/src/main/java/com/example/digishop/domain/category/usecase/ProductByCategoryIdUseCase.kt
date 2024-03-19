package com.example.digishop.domain.category.usecase

import com.example.digishop.data.remote.responses.product_response.toProduct
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.category.CategoryRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.error.getError
import com.example.digishop.domain.product.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductByCategoryIdUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend fun invoke(id:Int): Flow<AsyncResult<List<Product>>> = flow {

        val result = repository.productsByCategoryId(catId = id)

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val product = result.data.productApiModels.map { it.toProduct() }


        emit(AsyncResult.Success(product))

    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }
}