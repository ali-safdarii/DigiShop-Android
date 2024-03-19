package com.example.digishop.data.remote.util

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(
    @SerializedName("data")
    val `data`: T,
    @SerializedName("status_code")
    val statusCode: Int,
    val success: Boolean,
    val message: String,
)