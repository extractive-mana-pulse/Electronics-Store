package com.example.e_shop.home.domain.repository

import com.example.e_shop.home.domain.model.ProductItem

interface ShopRepository {

    suspend fun getAllProducts() : List<ProductItem>

    suspend fun getProductById(productId: Int): ProductItem
//
//    suspend fun getSortedProductsByCategory(category: String): Root
}