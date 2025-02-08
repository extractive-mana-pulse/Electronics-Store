package com.example.e_shop.catalog.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.catalog.data.remote.categories
import com.example.e_shop.catalog.presentation.vm.CatalogViewModel
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.navigation.screens.HomeScreens
import com.example.e_shop.navigation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesListScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(Unit) { viewModel.getSortedProductsByCategory() }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Shop by Categories",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.gabarito_regular)),
                            fontSize = 24.sp
                        )
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val result = state.value) {
                is Resource.Loading -> CircularLoadingProgress()
                is Resource.Success -> result.data?.let { categories ->
                    CategoriesScreen(categories) { category ->
                        navController.navigate(HomeScreens.Category.route)
                    }
                }
                is Resource.Error -> toastMessage(context, result.message ?: "An error occurred")
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    categoriesList: categories,
    onCategoryClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(categoriesList) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCategoryClick(category) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}