package com.example.e_shop.home.presentation.home.screen

import CategoriesSection
import SearchBarM3
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e_shop.R
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.Products
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.model.Specifications
import com.example.e_shop.home.presentation.home.vm.HomeViewModel
import com.example.e_shop.navigation.screens.Screens
import kotlinx.serialization.json.Json

@SuppressLint("AutoboxingStateCreation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    navController: NavController = rememberNavController(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(true) { viewModel.getAllProducts() }
    Scaffold(
        topBar = {
            SearchBarM3()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            when (val state = viewModel.state.value) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                is Resource.Success -> {
                    ProductItem(navController, viewModel, animatedVisibilityScope)
                }
                is Resource.Error -> {
                    Toast.makeText(
                        context,
                        state.message ?: "An unknown error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun SharedTransitionScope.ProductItem(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val state = viewModel.state.value
    val categories = remember { mutableSetOf<String>() }

    LazyColumn {
        item {
            CategoriesSection()
        }
        item {
            Extracted(
                state,
                categories,
                navController,
                animatedVisibilityScope
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
private fun SharedTransitionScope.Extracted(
    state: Resource<Root>,
    categories: MutableSet<String>,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    val allCategories = remember { mutableSetOf<String>() }

    LaunchedEffect(state.data?.products) {
        state.data?.products?.forEach { product ->
            product.category?.split(",")?.forEach { category ->
                allCategories.add(category.trim())
            }
        }
    }

    LazyRow {
        itemsIndexed(state.data?.products ?: emptyList()) { index, product ->
            product.let {
                categories.add(it.category.toString())
                val list = mutableListOf<String>()
                list.addAll(it.category.toString().split(","))
                Log.d("list",list.toString())
                Card(
                    modifier = Modifier
                        .width(250.dp)
                        .height(350.dp)
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(
                                Screens.Details(
                                    id = it.Id.toString(),
                                    name = it.name.toString(),
                                    price = it.price!!,
                                    image = it.image.toString(),
                                    description = it.description.toString(),
                                    specs = it.specifications,
                                    category = it.category.toString(),
                                    productImages = it.productImages
                                )
                            )
                        }
                )  {
                    ItemUI(
                        it,
                        product,
                        animatedVisibilityScope,
                        categories
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
private fun SharedTransitionScope.ItemUI(
    products: Products,
    product: Products,
    animatedVisibilityScope: AnimatedVisibilityScope,
    categories: MutableSet<String>
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        GlideImage(
            model = products.image,
            contentDescription = products.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .aspectRatio(16 / 9f)
                .weight(1f)
                .sharedElement(
                    state = rememberSharedContentState(key = "${product.image}"),
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categories.toString(),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.poppins_black)),
                fontWeight = FontWeight.Light
            ),
        )
    }
}