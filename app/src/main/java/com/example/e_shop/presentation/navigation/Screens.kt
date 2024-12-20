package com.example.e_shop.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object Home : Screens("home")

    @Serializable
    object Profile : Screens("profile")

    @Serializable
    data class Details(
        val id: String,
        val name: String,
        val price: Int,
        val image: String,
        val description: String,
        val specs: String
    ) : Screens ("details")
}
