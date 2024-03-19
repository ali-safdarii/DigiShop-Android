package com.example.digishop.data.remote.responses.cart.response

import com.google.gson.annotations.SerializedName

data class ProductExist(
    @SerializedName("isExist")
    val isExsit:Boolean,
    @SerializedName("qty")
    val qty: Int,
)
