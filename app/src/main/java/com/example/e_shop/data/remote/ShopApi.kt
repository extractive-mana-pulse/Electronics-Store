package com.example.e_shop.data.remote

import com.example.e_shop.domain.model.Root
import retrofit2.http.GET

interface ShopApi {
    @GET("api/products")
    suspend fun getAllProducts(): Root
}