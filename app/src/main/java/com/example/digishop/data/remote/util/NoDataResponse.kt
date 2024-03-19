package com.example.digishop.data.remote.util

import com.google.gson.annotations.SerializedName

data class NoDataResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    val message: String,
    val success: Boolean
)

