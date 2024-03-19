package com.example.digishop.data.remote.responses.product_response

import com.example.digishop.data.remote.responses.product_response.ImageIndexArray
import com.google.gson.annotations.SerializedName

data class ImageSizes(
    @SerializedName("indexArray")
    val indexArray: ImageIndexArray,
    @SerializedName("directory")
    val directory: String,
    @SerializedName("currentImage")
    val currentImageSize: String,
    @SerializedName("currentImageUrl")
    val currentImageUrl: String
)
