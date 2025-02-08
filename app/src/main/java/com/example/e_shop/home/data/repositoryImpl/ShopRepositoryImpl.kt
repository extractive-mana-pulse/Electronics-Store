package com.example.e_shop.home.data.repositoryImpl

import com.example.e_shop.home.data.remote.ShopApi
import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(private val api: ShopApi) : ShopRepository {

    override suspend fun getAllProducts(): List<ProductItem> = api.getAllProducts()

    override suspend fun getProductById(productId: Int): ProductItem = api.getProductById(productId)
//
//    override suspend fun getSortedProductsByCategory(category: String): Root {
//        val productsResponse = api.getSortedProductsByCategory(category)
//        val categorySet = mutableSetOf<String>()
//        productsResponse.products.forEach { product ->
//            product.category?.split(",")?.forEach { cat ->
//                categorySet.add(cat.trim())
//            }
//        }
//        return productsResponse
//    }
}