package com.example.e_shop.data.repositoryImpl

import com.example.e_shop.data.remote.ShopApi
import com.example.e_shop.domain.model.Root
import com.example.e_shop.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: ShopApi
) : ShopRepository {
    override suspend fun getAllProducts(): Root {
        return api.getAllProducts()
    }
}