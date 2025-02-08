package com.example.e_shop.home.presentation.home.screen

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
import coil3.compose.AsyncImage
import com.example.e_shop.R
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.core.util.SearchBarView
import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.presentation.vm.HomeViewModel
import com.example.e_shop.navigation.screens.HomeScreens
import com.example.e_shop.navigation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current

    Scaffold(
        topBar = { SearchBarView() }
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
                    // Assuming state.data is a List<ProductItem>
                    val products = state.data
                    products?.let { HeaderLayout(products = it, navController = navController) }
                }
                is Resource.Error -> {
                    toastMessage(context = context, message = state.message ?: "An unknown error occurred")
                }
            }
        }
    }
}

//@Composable
//fun ProductItem(
//    navController: NavController = rememberNavController(),
//    viewModel: HomeViewModel = hiltViewModel(),
//    catalogViewModel: CatalogViewModel = hiltViewModel()
//) {
//    val stateCat by catalogViewModel.state.collectAsState()
//    val state = viewModel.state.value
//    val context = LocalContext.current
//    val categories = remember(state) {
//        val categorySet = mutableSetOf<String>()
//        if (true) {
//            state.data?.products?.forEach { product ->
//                product.category?.split(",")?.forEach { category ->
//                    categorySet.add(category.trim())
//                }
//            }
//
//        }
//        categorySet
//    }

//    categories.forEach { category ->
//        LaunchedEffect(Unit) {
//            catalogViewModel.getSortedProductsByCategory(category)
//        }
//    }

//    LazyColumn {
        // all products
//        item { HeaderLayout(state, navController) }
        // all categories
//        items(categories.toList()) { category ->
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = category,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//                ProductsSection(navController = navController)
//            }
//        }
//    }
//}

//@Composable
//fun ProductsSection(
//    navController: NavController = rememberNavController()
//) {
//    val catalogViewModel: CatalogViewModel = hiltViewModel()
//    val state = catalogViewModel.state.collectAsState()
//    val context = LocalContext.current
//
//    val products = when (state.value) {
//        is Resource.Success -> state.value.data?.products ?: emptyList()
//        else -> emptyList()
//    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        when (state.value) {
//            is Resource.Loading -> { CircularLoadingProgress() }
//            is Resource.Success -> { ProductsByCategory(navController = navController, products = products) }
//            is Resource.Error -> {
//                toastMessage(
//                    context = context,
//                    message = state.value.message ?: "An unknown error occurred"
//                )
//            }
//        }
//    }
//}

//@Composable
//fun ProductsByCategory(
//    navController: NavController = rememberNavController(),
//    products: List<Products>
//) {
//    LazyRow {
//        itemsIndexed(products) { index, product ->
//            Card(
//                modifier = Modifier
//                    .width(250.dp)
//                    .height(350.dp)
//                    .padding(8.dp)
//                    .clickable {
//                        navController.navigate(
//                            Screens.Details(
//                                id = product._id
//                            )
//                        )
//                    }
//            ) { ItemUI(products = product) }
//        }
//        item { LastItem() }
//    }
//}

@Composable
private fun HeaderLayout(
    products: List<ProductItem?>, // Changed to accept a list of ProductItem
    navController: NavController = rememberNavController()
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

        LazyColumn {
            itemsIndexed(products) { index, product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(
                                HomeScreens.Details(
                                    id = product?.id ?: 0,
                                )
                            )
                        }
                ) {
                    ItemUI(products = product)
                }
            }
            item { LastItem() }
        }
    }
}

@Composable
private fun ItemUI(products: ProductItem?) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {

        AsyncImage(
            model = products?.image,
            contentDescription = products?.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .aspectRatio(16 / 9f)
                .weight(1f)
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = products?.title.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.poppins_black)),
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$${products?.price?.div(100)}",
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun LastItem() {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(350.dp)
            .padding(8.dp)
            .clickable { TODO("Navigate to all products screen") }
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
                )
            )
        }
    }
}