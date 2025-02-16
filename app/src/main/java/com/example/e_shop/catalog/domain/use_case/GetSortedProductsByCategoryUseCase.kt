package com.example.e_shop.catalog.domain.use_case

import com.example.e_shop.catalog.domain.model.CategoryProduct
import com.example.e_shop.catalog.domain.repository.CategoryRepository
import javax.inject.Inject

class GetSortedProductsByCategoryUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(category: String): List<CategoryProduct> = categoryRepository.loadSpecificCategory(category)

}