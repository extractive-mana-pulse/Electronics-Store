package com.example.e_shop.navigation.nav_host

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import com.example.e_shop.auth.presentation.GoogleAuthUiClient
//import com.example.e_shop.catalog.presentation.screens.CategoriesListScreen
//import com.example.e_shop.catalog.presentation.screens.CategoryProductsScreen
import com.example.e_shop.core.util.BottomNavigationBar
import com.example.e_shop.core.util.items
//import com.example.e_shop.home.presentation.detail.DetailScreen
import com.example.e_shop.navigation.screens.Graph
import com.example.e_shop.navigation.screens.HomeScreens
import com.example.e_shop.navigation.screens.Screens
import com.example.e_shop.profile.presentation.screens.AddPaymentScreen
import com.example.e_shop.profile.presentation.screens.PaymentScreen
import com.example.e_shop.profile.presentation.screens.ProfileScreen
import com.example.e_shop.profile.presentation.screens.SettingsPage
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    googleAuthUiClient: GoogleAuthUiClient
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
//    val navDrawerTopAppBar = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        HomeScreens.Home.route -> {
//            navDrawerTopAppBar.value = true
            bottomBarState.value = true
        }
        Screens.Profile.route -> {
//            navDrawerTopAppBar.value = false
            bottomBarState.value = true
        }
        HomeScreens.Catalog.route -> {
            bottomBarState.value = true
        }
        else -> {
//            navDrawerTopAppBar.value = false
            bottomBarState.value = false
        }
    }

    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
            bottomBar = {
                BottomNavigationBar(
                    bottomBarState = bottomBarState,
                    navController = navController
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Graph.AUTH,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                        top = if (navDrawerTopAppBar.value) innerPadding.calculateTopPadding() else 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                )
            ) {
                authNavGraph(
                    navController,
                    googleAuthUiClient
                )

                homeNavGraph(navController)

                composable(Screens.Profile.route) {
                    ProfileScreen(
                        navController = navController,
                        userData = googleAuthUiClient.getSignedInUser(),
                        googleAuthUiClient = googleAuthUiClient
                    )
                }
                composable(Screens.Payment.route) {
                    PaymentScreen(navController = navController)
                }
                composable(Screens.AddPayment.route) {
                    AddPaymentScreen(navController = navController)
                }
                composable(Screens.Settings.route) {
                    SettingsPage(navController = navController)
                }
            }
        }
    }
}