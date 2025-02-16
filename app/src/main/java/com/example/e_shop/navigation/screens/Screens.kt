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

    @Serializable
    object Payment: Screens("payment")

    @Serializable
    object AddPayment: Screens("add_payment")
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
    data class Details(val id: Int) : Screens ("details")

    @Serializable
    object Catalog : Screens("catalog")

    @Serializable
    data class Category(val name: String) : Screens("category")
}