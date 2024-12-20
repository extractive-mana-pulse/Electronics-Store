package com.example.e_shop.domain.repository

import com.example.e_shop.domain.model.Root

interface ShopRepository {

    suspend fun getAllProducts() : Root
}