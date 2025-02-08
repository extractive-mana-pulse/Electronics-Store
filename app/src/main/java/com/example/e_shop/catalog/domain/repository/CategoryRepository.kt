package com.example.e_shop.catalog.domain.repository

import com.example.e_shop.catalog.data.remote.categories

interface CategoryRepository {

    suspend fun getCategories(): categories

}