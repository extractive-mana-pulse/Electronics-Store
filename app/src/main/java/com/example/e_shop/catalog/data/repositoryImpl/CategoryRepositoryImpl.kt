package com.example.e_shop.catalog.data.repositoryImpl

import com.example.e_shop.catalog.data.remote.CategoryApi
import com.example.e_shop.catalog.data.remote.categories
import com.example.e_shop.catalog.domain.model.Categories
import com.example.e_shop.catalog.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val api : CategoryApi): CategoryRepository {
    override suspend fun getCategories(): categories {
        return api.getCategories()
    }
}