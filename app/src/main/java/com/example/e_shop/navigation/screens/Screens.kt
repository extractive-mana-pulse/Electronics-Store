package com.example.e_shop.navigation.screens

import kotlinx.serialization.Serializable

object Graph {
    const val AUTH = "auth_graph"
    const val HOME = "home_graph"
    const val ROOT = "root_graph"
}

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object Profile : Screens("profile")

    @Serializable
    object Settings: Screens("settings")
}

@Serializable
sealed class AuthScreens(val route: String) {

    @Serializable
    object Login : AuthScreens("login")

    @Serializable
    object Register : AuthScreens("register")

    @Serializable
    object ForgotPassword : AuthScreens("forgot_password")
}

sealed class HomeScreens(val route: String) {

    @Serializable
    object Home : Screens("home")

    @Serializable
    object Category : Screens("category")

    @Serializable
    data class Details(val id: Int) : Screens ("details")

    @Serializable
    data class Catalog(val name: String) : Screens("catalog")
}