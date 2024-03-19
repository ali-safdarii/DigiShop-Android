package com.example.digishop.data.remote.responses.product_response


import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_sizes")
    val imageSizes: ImageSizes?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("persian_name")
    val persianName: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("status")
    val status: Int?
)