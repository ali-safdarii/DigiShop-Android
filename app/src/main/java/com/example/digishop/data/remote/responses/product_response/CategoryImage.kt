package com.example.digishop.data.remote.responses.product_response

data class CategoryImage(
    val indexArray: ImageIndexArray,
    val directory: String,
    val currentImage: String
)