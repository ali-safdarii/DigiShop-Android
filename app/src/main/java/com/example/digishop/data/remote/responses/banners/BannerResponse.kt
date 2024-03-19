package com.example.digishop.data.remote.responses.banners

import com.example.digishop.data.remote.responses.banners.BannerItem
import com.google.gson.annotations.SerializedName


data class BannerResponse(
    @SerializedName("items")
    val banners: List<BannerItem>
)