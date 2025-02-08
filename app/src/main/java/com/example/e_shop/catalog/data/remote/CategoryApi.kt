package com.example.e_shop.catalog.data.remote

import retrofit2.http.GET

internal typealias categories = List<String>

interface CategoryApi {

    @GET("products/categories")
    suspend fun getCategories(): categories

}