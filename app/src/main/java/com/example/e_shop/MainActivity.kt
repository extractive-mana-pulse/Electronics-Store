package com.example.e_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.auth.presentation.GoogleAuthUiClient
import com.example.e_shop.home.presentation.vm.HomeViewModel
import com.example.e_shop.navigation.nav_host.AppNavigation
import com.example.e_shop.ui.theme.EshopTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .setKeepOnScreenCondition {
            !viewModel.isReady.value
        }
        super.onCreate(savedInstanceState)
        setContent {
            EshopTheme {
                val navController : NavHostController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    googleAuthUiClient = googleAuthUiClient
                )
            }
        }
    }
}