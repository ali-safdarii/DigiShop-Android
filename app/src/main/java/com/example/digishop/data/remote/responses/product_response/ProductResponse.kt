package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.data.remote.responses.product_response.Links
import com.example.digishop.data.remote.responses.product_response.Meta
import com.example.digishop.data.remote.responses.product_response.ProductApiModel
import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @SerializedName("data")
    val productData: List<ProductApiModel>,
    @SerializedName("items")
    val productApiModels: List<ProductApiModel>,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("meta")
    val meta: Meta
)