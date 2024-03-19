package com.example.digishop.data.remote.repository

import com.example.digishop.data.remote.datasource.BannerRemoteDataSource
import com.example.digishop.data.remote.responses.banners.BannerResponse
import com.example.digishop.data.remote.util.BaseResponse
import com.example.digishop.domain.banners.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(private val remoteDataSource: BannerRemoteDataSource) :
    BannerRepository {
    override suspend fun getBanners(): BaseResponse<BannerResponse> = remoteDataSource.getBanners()


}