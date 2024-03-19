package com.example.digishop.domain.banners

import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.util.BaseResponse

interface BannerRepository {
    suspend fun getBanners(
    ): BaseResponse<BannerResponse>
}