package com.example.e_shop.home.presentation.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.e_shop.R
import com.example.e_shop.catalog.presentation.vm.CatalogViewModel
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.core.util.SearchBarView
import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.presentation.vm.HomeViewModel
import com.example.e_shop.navigation.screens.HomeScreens
import java.util.Locale

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
                is Resource.Loading -> CircularLoadingProgress()
                is Resource.Success -> {
                    val products = state.data
                    products?.let {
                        HeaderLayout(
                            products = it,
                            navController = navController
                        )
                    }
                }
                is Resource.Error -> {
                    toastMessage(
                        context = context,
                        message = state.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderLayout(
    products: List<ProductItem?>,
    navController: NavController = rememberNavController()
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {  }
        item {
            Sections(
                products,
                navController
            )
        }
    }
}

@Composable
private fun Sections(
    products: List<ProductItem?>,
    navController: NavController
) {
    val context = LocalContext.current
    val categoryViewModel: CatalogViewModel = hiltViewModel()
    val categoriesState = categoryViewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (val result = categoriesState.value) {
            is Resource.Loading -> CircularLoadingProgress()
            is Resource.Error -> {
                toastMessage(
                    context = context,
                    message = result.message ?: "An unknown error occurred"
                )
            }
            is Resource.Success -> {
                val categories = result.data?: emptyList()
                categories.forEach { category ->
                    CategorySection(
                        category = category,
                        products = products.filter { it?.category == category },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySection(
    category: String,
    products: List<ProductItem?>,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = category.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else
                    it.toString()
            },
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily(Font(R.font.gabarito_bold))
            )
        )
        LazyRow {
            itemsIndexed(products.take(5)) { index, product ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(320.dp)
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
            if (products.size > 5) {
                item { LastItem() }
            }
        }
    }
}

@Composable
private fun ItemUI(products: ProductItem?) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        AsyncImage(
            model = products?.image,
            contentDescription = products?.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(bottom = 4.dp)
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = products?.title ?: "Product title not found!",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.poppins_black)),
                fontWeight = FontWeight.Light
            ),
            maxLines = 2,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$${products?.price ?: 0}",
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun LastItem() {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(320.dp)
            .padding(8.dp)
            .clickable {
                toastMessage(
                    context = context,
                    message = "Implement logic to navigate a screen with corresponding products"
                )
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
                text = "See all",
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