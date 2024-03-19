package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.data.remote.responses.product_response.DecodedValue
import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("category_attribute_id")
    val categoryAttributeId: Int?,
    @SerializedName("decoded_value")
    val decodedValue: DecodedValue?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("product_id")
    val productId: Int?
)