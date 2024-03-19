package com.example.digishop.data.remote.responses.product_response


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("url")
    val url: String?
)