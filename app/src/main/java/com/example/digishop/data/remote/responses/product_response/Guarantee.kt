package com.example.digishop.data.remote.responses.product_response


import com.google.gson.annotations.SerializedName

data class Guarantee(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price_increase")
    val priceIncrease: String?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("status")
    val status: Int?
)