package com.example.e_shop.navigation.nav_host

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_shop.catalog.presentation.CategoryProductsScreen
import com.example.e_shop.catalog.presentation.CategoriesListScreen
import com.example.e_shop.core.util.BottomNavigationBar
import com.example.e_shop.core.util.items
import com.example.e_shop.home.presentation.detail.DetailScreen
import com.example.e_shop.home.presentation.home.screen.HomeScreen
import com.example.e_shop.navigation.screens.Screens
import com.example.e_shop.profile.presentation.ProfileScreen
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
//    val navDrawerTopAppBar = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        Screens.Home.route -> {
//            navDrawerTopAppBar.value = true
            bottomBarState.value = true
        }
        Screens.Profile.route -> {
//            navDrawerTopAppBar.value = false
            bottomBarState.value = true
        }
        Screens.Category.route -> {
            bottomBarState.value = true
        }
        else -> {
//            navDrawerTopAppBar.value = false
            bottomBarState.value = false
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val drawerGestureEnabled = navBackStackEntry?.destination?.route in listOf("")

    ModalNavigationDrawer(
        gesturesEnabled = drawerGestureEnabled,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
//                          navController.navigate(item.route)
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
//            topBar = {
//                if (navDrawerTopAppBar.value) {
//                    AnimatedVisibility(
//                        visible = navDrawerTopAppBar.value,
//                        enter = expandHorizontally() + fadeIn(),
//                        exit = shrinkHorizontally() + fadeOut(),
//                        content = {
//                            SearchBarM3()
//                        }
//                    )
//                }
//            },
            bottomBar = {
                BottomNavigationBar(
                    bottomBarState = bottomBarState,
                    navController = navController
                )
            }
        ) { innerPadding ->

            SharedTransitionLayout {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.route,
                    modifier = Modifier.padding(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                        top = if (navDrawerTopAppBar.value) innerPadding.calculateTopPadding() else 0.dp,
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                    )
                ) {
                    composable(Screens.Home.route) {
                        HomeScreen(
                            navController = navController,
                            animatedVisibilityScope = this
                        )
                    }

                    composable(Screens.Category.route) {
                        CategoriesListScreen(navController = navController)
                    }

                    composable<Screens.Catalog> {
                        val argument = it.toRoute<Screens.Catalog>()
                        CategoryProductsScreen(
                            navController = navController,
                            name = argument.name
                        )
                    }

                    composable(Screens.Profile.route) {
                        ProfileScreen(navController = navController)
                    }

                    composable<Screens.Details>(
//                        typeMap = mapOf(
//                            typeOf<Specifications>() to CustomNavType.specType,
//                            typeOf<ProductImages>() to CustomNavType.picType
//                        )
                    ) {
                        val argument = it.toRoute<Screens.Details>()
                        DetailScreen(
                            navController = navController,
                            animatedVisibilityScope = this,
                            id = argument.id,
//                            id = argument.id,
//                            name = argument.name,
//                            price = argument.price,
//                            image = argument.image,
//                            description = argument.description,
//                            specs = argument.specs,
//                            category = argument.category,
//                            productImages = argument.productImages,
//                            features = argument.features,
//                            colors = argument.colors
                        )
                    }
                }
            }
        }
    }
}