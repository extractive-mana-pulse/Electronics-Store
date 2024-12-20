package com.example.e_shop.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.e_shop.presentation.detail.DetailScreen
import com.example.e_shop.presentation.home.screen.HomeScreen
import com.example.e_shop.presentation.profile.ProfileScreen
import com.example.e_shop.presentation.util.ui.BottomNavigationBar
import com.example.e_shop.presentation.util.ui.items
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navDrawerTopAppBar = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        Screens.Home.route -> {
            navDrawerTopAppBar.value = true
            bottomBarState.value = true
        }
        Screens.Profile.route -> {
            navDrawerTopAppBar.value = false
            bottomBarState.value = true
        }
        else -> {
            navDrawerTopAppBar.value = false
            bottomBarState.value = false
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val drawerGestureEnabled = navBackStackEntry?.destination?.route in listOf(Screens.Home.route)


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
            topBar = {
                if (navDrawerTopAppBar.value) {
                    AnimatedVisibility(
                        visible = navDrawerTopAppBar.value,
                        enter = expandHorizontally() + fadeIn(),
                        exit = shrinkHorizontally() + fadeOut(),
                        content = {
                            TopAppBar(
                                title = {
                                    Text(text = "Products")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu"
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            },
            bottomBar = {
                BottomNavigationBar(bottomBarState = bottomBarState, navController = navController)
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Screens.Home.route,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    // Only apply top padding if top bar is visible
                    top = if (navDrawerTopAppBar.value) innerPadding.calculateTopPadding() else 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    // Only apply bottom padding if bottom bar is visible
                    bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                )
            ) {
                composable(
                    Screens.Home.route,
                    enterTransition = { expandHorizontally() + fadeIn() },
                    exitTransition = { shrinkHorizontally() + fadeOut() }
                ) {
                    HomeScreen(navController = navController)
                }
                composable(
                    Screens.Profile.route,
                    enterTransition = { expandHorizontally() + fadeIn() },
                    exitTransition = { shrinkHorizontally() + fadeOut() }
                ) {
                    ProfileScreen(navController = navController)
                }
                composable<Screens.Details> {
                    val argument = it.toRoute<Screens.Details>()
                    DetailScreen(
                        navController = navController,
                        id = argument.id,
                        name = argument.name,
                        price = argument.price,
                        image = argument.image,
                        description = argument.description,
                        specs = argument.specs
                    )
                }
            }
        }
    }
}