package com.example.digishop.di

import com.example.digishop.data.remote.datasource.AuthRemoteDataSource
import com.example.digishop.data.remote.datasource.AuthRemoteImpl
import com.example.digishop.data.remote.datasource.BannerRemoteDataSource
import com.example.digishop.data.remote.datasource.BannerRemoteImpl
import com.example.digishop.data.remote.datasource.CategoryRemoteImpl
import com.example.digishop.data.remote.datasource.CartRemoteImpl
import com.example.digishop.data.remote.datasource.CartRemoteDataSource
import com.example.digishop.data.remote.datasource.CategoryRemoteDataSource
import com.example.digishop.data.remote.datasource.ProductRemoteDataSource
import com.example.digishop.data.remote.datasource.ProductRemoteImpl
import com.example.digishop.data.remote.service.AuthService
import com.example.digishop.data.remote.service.BannerService
import com.example.digishop.data.remote.service.CartService
import com.example.digishop.data.remote.service.CategoryService
import com.example.digishop.data.remote.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSoruceModule {

    @Provides
    @Singleton
    fun providecartDataSource(
        cartService: CartService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher

    ): CartRemoteDataSource = CartRemoteImpl(cartService, ioDispatcher)


    @Provides
    @Singleton
    fun provideProductRemoteDataSource(
        productService: ProductService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ProductRemoteDataSource = ProductRemoteImpl(productService, ioDispatcher)


    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(
        categoryService: CategoryService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CategoryRemoteDataSource =
        CategoryRemoteImpl(categoryService = categoryService, ioDispatcher = ioDispatcher)


    @Provides
    @Singleton
    fun provideBannerRemoteDataSource(
        bannerService: BannerService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): BannerRemoteDataSource =
        BannerRemoteImpl(bannerService = bannerService, ioDispatcher = ioDispatcher)


    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        authService: AuthService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthRemoteDataSource =
        AuthRemoteImpl(authService = authService, ioDispatcher = ioDispatcher)

}