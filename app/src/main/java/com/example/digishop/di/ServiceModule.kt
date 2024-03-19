package com.example.digishop.di

import com.example.digishop.data.remote.service.AuthService
import com.example.digishop.data.remote.service.BannerService
import com.example.digishop.data.remote.service.CartService
import com.example.digishop.data.remote.service.CategoryService
import com.example.digishop.data.remote.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {





    @Singleton
    @Provides
    fun provideAuthService(@AppModule.NormalType retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideCartService(@AppModule.NormalType retrofit: Retrofit): CartService {
        return retrofit.create(CartService::class.java)
    }


    @Singleton
    @Provides
    fun provideProductService(@AppModule.NormalType retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryService(@AppModule.NormalType retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideBannerService(@AppModule.NormalType retrofit: Retrofit): BannerService {
        return retrofit.create(BannerService::class.java)
    }
}