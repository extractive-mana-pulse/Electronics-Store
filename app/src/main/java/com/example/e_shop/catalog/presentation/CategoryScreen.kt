package com.example.e_shop.catalog.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.e_shop.core.extensions.getCategoryInfo
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.presentation.home.vm.HomeViewModel
import com.example.e_shop.navigation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Categories") },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LaunchedEffect(true) { viewModel.getAllProducts() }
            when (state) {
                is Resource.Error -> { Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show() }
                is Resource.Loading -> { CircularProgressIndicator() }
                is Resource.Success -> {
                    val categories = remember(state) {
                        val categorySet = mutableSetOf<String>()
                        if (true) {
                            state.data?.products?.forEach { product ->
                                product.category?.split(",")?.forEach { category ->
                                    categorySet.add(category.trim())
                                }
                            }
                        }
                        categorySet
                    }
                    CategoriesScreen(
                        categories = categories,
                        onCategoryClick = { category ->
                            navController.navigate(
                                Screens.Catalog(
                                    name = category
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    categories: Set<String>,
    onCategoryClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(categories.toList()) { category ->
            val categoryInfo = getCategoryInfo(category)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCategoryClick(category) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = categoryInfo.imageResource,
                    contentDescription = "${categoryInfo.displayName} Image",
                    modifier = Modifier.padding(16.dp).size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Text(
                    text = categoryInfo.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}