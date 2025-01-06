package com.example.e_shop.home.domain.repository

import com.example.e_shop.home.domain.model.Root

interface ShopRepository {

    suspend fun getAllProducts() : Root
}