package com.example.e_shop.catalog.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.e_shop.R
import com.example.e_shop.catalog.domain.model.CategoryProduct
import com.example.e_shop.catalog.presentation.vm.CatalogViewModel
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.navigation.screens.HomeScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryProductsScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    name: String,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val categoryState = viewModel.categoryState.collectAsStateWithLifecycle()

    LaunchedEffect(name) { viewModel.getSpecificCategoryProducts(name) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CategoryTopBar(
                name = name,
                productsCount = categoryState.value.data?.size ?: 0,
                onBackClick = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val result = categoryState.value) {
                is Resource.Loading -> CircularLoadingProgress()
                is Resource.Success -> {
                    val products = result.data
                    CategoryProductsGrid(
                        products = products ?: emptyList(),
                        onProductClick = { product ->
                            navController.navigate(
                                HomeScreens.Details(
                                    id = product.id ?: 0
                                )
                            )
                        }
                    )
                }
                is Resource.Error -> {
                    toastMessage(
                        context = LocalContext.current,
                        message = result.message.toString()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryTopBar(
    name: String,
    productsCount: Int,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.backToCatalog)
                )
            }
        },
        title = {
            Text(
                text = "$name ($productsCount)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                    fontSize = 18.sp
                ),
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun CategoryProductsGrid(
    products: List<CategoryProduct>,
    onProductClick: (CategoryProduct) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(products.size) { product ->
            ProductCard(
                product = products[product],
                onProductClick = onProductClick
            )
        }
    }
}

@Composable
fun ProductCard(
    product: CategoryProduct,
    onProductClick: (CategoryProduct) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick(product) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = product.image ?: Icons.Default.Close,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp, bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = product.rating?.rate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}