package com.example.digishop.domain.category.usecase

import com.example.digishop.data.remote.responses.product_response.Category
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.category.CategoryRepository
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.error.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(private val repository: CategoryRepository) {


    suspend fun invoke(): Flow<AsyncResult<List<Category>>> = flow {

        val result = repository.categories()

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val product = result.data.categoryList


        emit(AsyncResult.Success(product))


    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }
}