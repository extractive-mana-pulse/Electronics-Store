package com.example.e_shop.domain.use_case

import com.example.e_shop.domain.model.Root
import com.example.e_shop.domain.repository.ShopRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val shopRepository: ShopRepository) {

    suspend operator fun invoke(): Root = shopRepository.getAllProducts()
}