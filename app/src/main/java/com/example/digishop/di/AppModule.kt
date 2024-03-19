package com.example.digishop.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.digishop.DataStoreToken
import com.example.digishop.data.local.datastore.LocalUserManagerImpl
import com.example.digishop.data.local.proto.tokenDataStore
import com.example.digishop.domain.manager.repository.LocalUserManager
import com.example.digishop.domain.manager.usecase.AppEntryUseCases
import com.example.digishop.domain.manager.usecase.ReadAppEntry
import com.example.digishop.domain.manager.usecase.SaveAppEntry
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


const val BASE_URL = "http://127.0.0.1:8000/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NormalType


    @NormalType
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }


    @NormalType
    @Singleton
    @Provides
    fun providesRetrofit(@NormalType okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    @Provides
    fun provideTokenDataStore(
        @ApplicationContext
        applicationContext: Context,
    ): DataStore<DataStoreToken> {
        return applicationContext.tokenDataStore
    }


    @Provides
    @Singleton
    fun provideAppEntry(@ApplicationContext context: Context): LocalUserManager =
        LocalUserManagerImpl(context)


    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


}