package com.example.e_shop.catalog.domain.repository

import com.example.e_shop.catalog.data.remote.categories
import com.example.e_shop.catalog.domain.model.CategoryProduct

interface CategoryRepository {

    suspend fun getCategories(): categories

    suspend fun loadSpecificCategory(category: String) : List<CategoryProduct>
}