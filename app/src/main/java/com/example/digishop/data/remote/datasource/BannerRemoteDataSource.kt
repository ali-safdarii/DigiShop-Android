package com.example.digishop.data.remote.datasource

import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.service.BannerService
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface BannerRemoteDataSource {

    suspend fun getBanners(
    ): BaseResponse<BannerResponse>

}

class BannerRemoteImpl @Inject constructor(
    private val bannerService: BannerService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BannerRemoteDataSource {
    override suspend fun getBanners(): BaseResponse<BannerResponse> =
        withContext(ioDispatcher) {
            bannerService.getBanners()
        }

}