package com.example.e_shop.home.domain.use_case

import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.domain.repository.ShopRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val shopRepository: ShopRepository) {
    suspend operator fun invoke() : List<ProductItem> = shopRepository.getAllProducts()
}