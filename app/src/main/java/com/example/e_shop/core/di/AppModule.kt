package com.example.e_shop.core.di

import com.example.e_shop.BuildConfig
import com.example.e_shop.home.data.remote.ShopApi
import com.example.e_shop.home.data.repositoryImpl.ShopRepositoryImpl
import com.example.e_shop.home.domain.repository.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): ShopApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
            )
            .build()
            .create(ShopApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: ShopApi): ShopRepository {
        return ShopRepositoryImpl(api)
    }
}