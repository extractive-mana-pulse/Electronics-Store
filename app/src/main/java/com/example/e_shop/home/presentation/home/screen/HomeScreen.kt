package com.example.e_shop.home.presentation.home.screen

import SearchBarM3
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.e_shop.R
import com.example.e_shop.catalog.presentation.CatalogViewModel
import com.example.e_shop.core.extensions.getCategoryInfo
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.home.domain.model.Products
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.presentation.home.vm.HomeViewModel
import com.example.e_shop.navigation.screens.Screens
import kotlin.text.split

@SuppressLint("AutoboxingStateCreation")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.HomeScreen(
    navController: NavController = rememberNavController(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(true) { viewModel.getAllProducts() }

    Scaffold(
        topBar = { SearchBarM3() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (val state = viewModel.state.value) {
                is Resource.Loading -> {
                    CircularLoadingProgress()
                }
                is Resource.Success -> {
                    ProductItem(
                        navController,
                        viewModel,
                        animatedVisibilityScope
                    )
                }
                is Resource.Error -> {
                    toastMessage(context = context, message = state.message ?: "An unknown error occurred",)
                }
            }
        }
    }
}

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SharedTransitionScope.ProductItem(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val state = viewModel.state.value
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

    LazyColumn {
        item {
            ProductSection(
                state,
                navController,
                animatedVisibilityScope
            )
        }
        items(categories.toList()) { category ->
            val categoryInfo = getCategoryInfo(category)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = categoryInfo.imageResource,
                        contentDescription = "${categoryInfo.displayName} Image",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                    Text(
                        text = categoryInfo.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                MobileSection(
                    categoryInfo.displayName,
                    navController,
                    animatedVisibilityScope
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun SharedTransitionScope.MobileSection(
    categoryName: String,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val catalogViewModel : CatalogViewModel = hiltViewModel()
    LaunchedEffect(true) { catalogViewModel.getSortedProductsByCategory(categoryName) }
    val state1 = catalogViewModel.state.value
    val context = LocalContext.current

    Log.d("HomeScreen", "MobileSection: $categoryName")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Todo: Implement all categories here.
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun SharedTransitionScope.ProductSection(
    state: Resource<Root>,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All products",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        LazyRow {
            itemsIndexed(state.data?.products?.take(5) ?: emptyList()) { index, product ->
                product.let {
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .height(350.dp)
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Screens.Details(
                                        id = it._id,
                                    )
                                )
                            }
                    ) {
                        ItemUI(
                            products = it,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .width(250.dp)
                        .height(350.dp)
                        .padding(8.dp)
                        .clickable {
                            // Your custom click action
//                            navController.navigate(Screens.AllProducts.route)
                        }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = "See all",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "See All Products",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily(
                                    Font(R.font.gabarito_variable_font_wght)
                            ),
                            fontWeight = FontWeight.Bold
                        ))
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun SharedTransitionScope.ItemUI(
    products: Products,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {

        AsyncImage(
            model = products.image,
            contentDescription = products.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .aspectRatio(16 / 9f)
                .weight(1f)
                .sharedElement(
                    state = rememberSharedContentState(key = "${products.image}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initialRect, targetRect ->
                        spring(
                            dampingRatio = 0.8f,
                            stiffness = 380f
                        )
                    },
                )
        )
        HorizontalDivider()

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = products.name ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.poppins_black)),
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$${products.price?.div(100).toString()}",
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}