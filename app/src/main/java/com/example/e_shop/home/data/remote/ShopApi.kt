package com.example.e_shop.home.data.remote

import com.example.e_shop.home.domain.model.ProductItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductItem>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductItem

//    @GET("/api/products")
//    suspend fun getSortedProductsByCategory(@Query("category") category: String): Root
}