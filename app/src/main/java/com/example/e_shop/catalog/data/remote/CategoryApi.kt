package com.example.e_shop.catalog.data.remote

import com.example.e_shop.catalog.domain.model.CategoryProduct
import retrofit2.http.GET
import retrofit2.http.Path

internal typealias categories = List<String>

interface CategoryApi {

    @GET("products/categories")
    suspend fun getCategories(): categories

    @GET("products/category/{category}")
    suspend fun loadSpecificCategory(@Path("category") category: String) : List<CategoryProduct>

}