package com.example.e_shop.home.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class ProductItem(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)