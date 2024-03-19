package com.example.digishop.data.remote.util

import com.example.digishop.domain.error.CustomErrorType

private const val RETRY_TIME_IN_MILLIS = 15_000L
sealed class AsyncResult<out T> {
    data class Success<out T>(val data: T) : AsyncResult<T>()
    data class Failure<T>(val error: CustomErrorType) : AsyncResult<T>()

}

inline fun <reified T> AsyncResult<T>.onSuccess(action: (T) -> Unit): AsyncResult<T> {
    if (this is AsyncResult.Success) {
        action(data)
    }
    return this
}

inline fun <reified T> AsyncResult<T>.onFailure(action: (CustomErrorType) -> Unit): AsyncResult<T> {
    if (this is AsyncResult.Failure) {
        action(error)
    }
    return this
}

