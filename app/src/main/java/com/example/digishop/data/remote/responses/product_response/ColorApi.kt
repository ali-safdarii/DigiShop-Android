package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.domain.product.model.ProductColor
import com.google.gson.annotations.SerializedName

data class ColorApi(
    @SerializedName("color_code")
    val colorCode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_increase")
    val priceIncrease: Int,


)


fun ColorApi.toColor(): ProductColor = ProductColor(
    colorCode = colorCode,
    colorName = name,
    price = priceIncrease,
    id = id,
)