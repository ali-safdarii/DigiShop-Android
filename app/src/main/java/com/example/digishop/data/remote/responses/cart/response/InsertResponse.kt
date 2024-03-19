package com.example.digishop.data.remote.responses.cart.response


import com.google.gson.annotations.SerializedName

data class InsertResponse(
    @SerializedName("cart_id")
    val cartId: Int,
    @SerializedName("color_id")
    val colorId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)