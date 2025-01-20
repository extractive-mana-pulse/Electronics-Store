package com.example.e_shop.home.data.remote

import com.example.e_shop.home.domain.model.Root
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopApi {

    @GET("api/products")
    suspend fun getAllProducts(): Root

    @GET("/api/products")
    suspend fun getProductById(@Query("_id") id: String): Root
}