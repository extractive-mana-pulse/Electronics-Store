package com.example.e_shop.home.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.e_shop.R
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.core.util.DetailsScreenTopAppBar
import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.presentation.vm.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun DetailScreen(
    navController: NavHostController = rememberNavController(),
    id: Int
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.productById.value

    LaunchedEffect(Unit) { viewModel.getProductById(id) }

    Scaffold(
        topBar = {
            DetailsScreenTopAppBar(
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is Resource.Error -> toastMessage(context, state.message ?: "An unexpected error occurred")
                is Resource.Loading -> CircularLoadingProgress()
                is Resource.Success -> if (state.data != null) DetailContent(state.data) else toastMessage(context, "Product not found")
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class,
)
@Composable
fun DetailContent(product: ProductItem) {

    val count = remember { mutableIntStateOf(1) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .safeDrawingPadding()
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize().height(300.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                    ),
                )
                Text(
                    text = "$${product.price}$",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                QuantityUI(
                    count = count.intValue,
                    onCountChange = { count.intValue = it }
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.poppins_light)),
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        Button(
            onClick = { TODO("Add to cart logic") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$${product.price * count.intValue}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = stringResource(R.string.add_to_bag),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.poppins_light)),
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
fun QuantityUI(count: Int, onCountChange: (Int) -> Unit) {

    val buttonEnabled = remember { mutableStateOf(count > 0) }
    LaunchedEffect(count) { buttonEnabled.value = count > 0 }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Quantity",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(
            onClick = { onCountChange(count + 1) },
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(text = count.toString())
        IconButton(
            onClick = { if (count > 0) onCountChange(count - 1) },
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary),
            enabled = buttonEnabled.value
        ) {
            Icon(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Remove",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}