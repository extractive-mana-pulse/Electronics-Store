package com.example.e_shop.navigation.screens

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
    ) : Screens ("details")

    @Serializable
    object Settings: Screens("settings")
}