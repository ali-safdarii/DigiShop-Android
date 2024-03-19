package com.example.digishop.domain.error

import androidx.annotation.StringRes
import com.example.digishop.R
import okhttp3.internal.http.HTTP_BAD_REQUEST
import okhttp3.internal.http.HTTP_NOT_FOUND
import okhttp3.internal.http.HTTP_TOO_MANY_REQUESTS
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


data class ErrorTypeMessage(
    @StringRes val stringRes: Int? = null,
    val localMessage: String? = null
)

sealed class CustomErrorType(val errorTypeMessage: ErrorTypeMessage = ErrorTypeMessage()) {
    object IOException : CustomErrorType()
    object NotFound : CustomErrorType(errorTypeMessage = ErrorTypeMessage(stringRes = R.string.socket_time_out_message))
    object UNAUTHORIZED : CustomErrorType()
    object BadRequest : CustomErrorType()
    object CredentialsException : CustomErrorType()
    object SocketTimeoutException :
        CustomErrorType(errorTypeMessage = ErrorTypeMessage(stringRes = R.string.socket_time_out_message))

    object ToManyRequest :
        CustomErrorType(errorTypeMessage = ErrorTypeMessage(stringRes = R.string.err_too_many_request))

    object Unknown :
        CustomErrorType(errorTypeMessage = ErrorTypeMessage(stringRes = R.string.err_unknown))

    data class CustomMessage(val message: String) :
        CustomErrorType(errorTypeMessage = ErrorTypeMessage(localMessage = message))


}

fun getError(throwable: Throwable): CustomErrorType {
    Timber.e(throwable)
    return when (throwable) {
        is HttpException -> {
            when (throwable.code()) {

                HTTP_NOT_FOUND -> CustomErrorType.NotFound

                HTTP_UNAUTHORIZED -> CustomErrorType.UNAUTHORIZED

                HTTP_BAD_REQUEST -> CustomErrorType.BadRequest

                HTTP_TOO_MANY_REQUESTS -> CustomErrorType.ToManyRequest

                else -> CustomErrorType.Unknown
            }
        }

        is IOException -> CustomErrorType.IOException
        //  is SocketTimeoutException -> CustomErrorType.SocketTimeoutException
        is InvalidCredentialsException -> CustomErrorType.CredentialsException


        else -> CustomErrorType.Unknown

    }
}

