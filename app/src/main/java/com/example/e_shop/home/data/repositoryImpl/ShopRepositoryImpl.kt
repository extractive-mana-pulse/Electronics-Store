package com.example.e_shop.home.data.repositoryImpl

import android.util.Log
import com.example.e_shop.home.data.remote.ShopApi
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(private val api: ShopApi) : ShopRepository {

    override suspend fun getAllProducts(): Root = api.getAllProducts()

    override suspend fun getProductById(productId: String): Root {
        Log.d("Repository", "Fetching product with ID: $productId")
        val product = api.getProductById(productId)
        Log.d("Repository", "Fetched product: $product")
        return product
    }
}