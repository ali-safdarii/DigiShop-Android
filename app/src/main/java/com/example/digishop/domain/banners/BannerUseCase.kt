package com.example.digishop.domain.banners

import com.example.digishop.data.remote.responses.banners.BannerItem
import com.example.digishop.data.remote.util.AsyncResult
import com.example.digishop.domain.error.CustomErrorType
import com.example.digishop.domain.error.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BannerUseCase @Inject constructor(private val repository: BannerRepository) {

    suspend fun invoke():Flow<AsyncResult<List<BannerItem>>> = flow {

        val  result = repository.getBanners()

        if (!result.success) {
            emit(AsyncResult.Failure(CustomErrorType.CustomMessage(message = result.message)))
            return@flow
        }

        val product = result.data.banners


        emit(AsyncResult.Success(product))


    }.catch {
        emit(AsyncResult.Failure(getError(it)))
    }
}