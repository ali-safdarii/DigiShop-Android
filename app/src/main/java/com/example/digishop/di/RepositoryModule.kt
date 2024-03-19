package com.example.digishop.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.digishop.DataStoreToken
import com.example.digishop.data.local.datastore.LocalUserManagerImpl
import com.example.digishop.data.local.proto.ProtoTokenRepository
import com.example.digishop.data.remote.datasource.AuthRemoteDataSource
import com.example.digishop.data.remote.datasource.BannerRemoteDataSource
import com.example.digishop.data.remote.datasource.CartRemoteDataSource
import com.example.digishop.data.remote.datasource.CategoryRemoteDataSource
import com.example.digishop.data.remote.datasource.ProductRemoteDataSource
import com.example.digishop.data.remote.repository.AuthRepositoryImpl
import com.example.digishop.data.remote.repository.BannerRepositoryImpl
import com.example.digishop.data.remote.repository.CartRepositoryImpl
import com.example.digishop.data.remote.repository.CategoryRepositoryImpl
import com.example.digishop.data.remote.repository.ProductRepositoryImpl
import com.example.digishop.domain.auth.repository.AuthRepository
import com.example.digishop.domain.banners.BannerRepository
import com.example.digishop.domain.cart.repository.CartRepository
import com.example.digishop.domain.category.CategoryRepository
import com.example.digishop.domain.manager.repository.LocalUserManager
import com.example.digishop.domain.product.repository.ProductRepository
import com.example.digishop.domain.token.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {



    @Provides
    @Singleton
    fun provideTokenRepository(tokenDataStore: DataStore<DataStoreToken>): TokenRepository {
        return ProtoTokenRepository(tokenDataStore)
    }



    @Provides
    @Singleton
    fun provideProductRepository(
        productRemoteDataSource: ProductRemoteDataSource,
    ): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCartRepositoryImp(cartRemoteDataSource: CartRemoteDataSource): CartRepository {
        return CartRepositoryImpl(cartRemoteDataSource)
    }


    @Provides
    @Singleton
    fun provideCategoryRepositoryImp(
        categoryRemoteDataSource: CategoryRemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryRemoteDataSource)
    }

    @Provides
    @Singleton
    fun providBannerRepositoryImp(
        bannerRemoteDataSource: BannerRemoteDataSource
    ): BannerRepository {
        return BannerRepositoryImpl(bannerRemoteDataSource)
    }


    @Provides
    @Singleton
    fun providAuthRepositoryImpl(
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }


}





























