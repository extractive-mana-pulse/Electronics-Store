package com.example.e_shop.navigation.nav_host

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_shop.auth.presentation.screens.AuthScreen
import com.example.e_shop.auth.presentation.screens.LoginScreen
import com.example.e_shop.navigation.screens.AuthScreens
import com.example.e_shop.navigation.screens.Graph

fun NavGraphBuilder.authNavGraph(navController: NavHostController){

    navigation(
        startDestination = AuthScreens.Login.route,
        route = Graph.AUTH
    ) {
        composable(AuthScreens.Login.route) {
            LoginScreen(navController)
        }
        composable(AuthScreens.Register.route) {
            AuthScreen(navController)
        }
    }
}