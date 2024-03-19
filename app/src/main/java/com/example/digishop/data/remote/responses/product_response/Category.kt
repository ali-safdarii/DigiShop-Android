package com.example.digishop.data.remote.responses.product_response

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val slug: String,
    @SerializedName("image_sizes")
    val imageSizes: ImageSizes,
    val status: Int,
    @SerializedName("full_category_name")
    val fullParentsName: String,
    @SerializedName("last_two_parents")
    val lastTwoParents: String,
)


