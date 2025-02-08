package com.example.e_shop.home.domain.model


import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)