package com.example.digishop.data.remote.responses.amazing_products.response


import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.google.gson.annotations.SerializedName

data class AmazingData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("percentage")
    val percentage: Int,
    @SerializedName("product")
    val productApiModel: ProductApiModel,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("status")
    val status: Int,
)