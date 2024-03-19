package com.example.digishop.data.remote.responses.banners


import com.example.digishop.di.BASE_URL
import com.google.gson.annotations.SerializedName

data class BannerItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_used_mobile")
    val isUsedMobile: Int,
    @SerializedName("position")
    val position: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String
){
    val imageUrl: String
        get() = BASE_URL + image
}