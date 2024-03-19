package com.example.digishop.data.remote.responses.product_response

import com.google.gson.annotations.SerializedName

data class Discount(
    val percentage:Int,
    val discountAmount:Int,
    val finalPrice:Int,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("start_date")
    val startDate: String,
)
