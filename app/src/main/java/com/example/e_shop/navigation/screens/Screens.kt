package com.example.e_shop.navigation.screens

import com.example.e_shop.home.domain.model.ProductImages
import com.example.e_shop.home.domain.model.Specifications
import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object Home : Screens("home")

    @Serializable
    object Profile : Screens("profile")

    @Serializable
    object Category : Screens("Category")

    @Serializable
    data class Catalog(
        val name: String,
    ) : Screens("Catalog")

    @Serializable
    data class Details(
        val id: String,
//        val name: String,
//        val price: Int,
//        val image: String,
//        val description: String,
//        val specs: Specifications,
//        val category: String,
//        val productImages: ProductImages,
//        val features: String,
//        val colors: String,
    ) : Screens ("details")
}