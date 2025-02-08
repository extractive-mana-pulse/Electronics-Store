package com.example.e_shop.navigation.nav_host

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.e_shop.catalog.presentation.screens.CategoriesListScreen
import com.example.e_shop.home.presentation.detail.DetailScreen
import com.example.e_shop.home.presentation.home.screen.HomeScreen
import com.example.e_shop.navigation.screens.Graph
import com.example.e_shop.navigation.screens.HomeScreens

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        startDestination = HomeScreens.Home.route,
        route = Graph.HOME,
    ) {
        composable(HomeScreens.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(HomeScreens.Category.route) {
            CategoriesListScreen(navController = navController)
        }

        composable<HomeScreens.Catalog> {
            val argument = it.toRoute<HomeScreens.Catalog>()
//                        CategoryProductsScreen(
//                            navController = navController,
//                            name = argument.name
//                        )
        }

        composable<HomeScreens.Details>(
//                        typeMap = mapOf(
//                            typeOf<Specifications>() to CustomNavType.specType,
//                            typeOf<ProductImages>() to CustomNavType.picType
//                        )
        ) {
            val argument = it.toRoute<HomeScreens.Details>()
            DetailScreen(
                navController = navController,
                id = argument.id,
            )
        }
    }
}