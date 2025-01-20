package com.example.e_shop.home.domain.use_case

import android.util.Log
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.repository.ShopRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val shopRepository: ShopRepository) {

    suspend operator fun invoke(id: String): Root {
        Log.d("UseCase", "Invoking getProductById with ID: $id")
        return shopRepository.getProductById(id)
    }
}