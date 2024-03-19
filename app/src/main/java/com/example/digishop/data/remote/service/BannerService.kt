package com.example.digishop.data.remote.service

import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.util.BaseResponse
import retrofit2.http.GET

interface BannerService {
    @GET("api/banners")
    suspend fun getBanners(
    ): BaseResponse<BannerResponse>

}