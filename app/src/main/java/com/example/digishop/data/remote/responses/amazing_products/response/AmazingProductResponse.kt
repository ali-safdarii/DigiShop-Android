package com.example.digishop.data.remote.responses.amazing_products.response


import com.google.gson.annotations.SerializedName

data class AmazingProductResponse(
    @SerializedName("items")
    val amazingDataList: List<AmazingData>
)