package com.example.digishop.data.remote.responses.product_response


import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("id") val id: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("image_sizes") val imageSizes: ImageSizes,

    )