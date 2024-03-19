package com.example.digishop.data.remote.responses.product_response


import com.example.digishop.data.remote.responses.product_response.Category
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("items")
    val categoryList: List<Category>
)