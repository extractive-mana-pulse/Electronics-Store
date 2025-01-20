package com.example.e_shop.home.presentation.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.e_shop.R
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.Products
import com.example.e_shop.home.presentation.home.vm.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.text.split

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun SharedTransitionScope.DetailScreen(
    navController: NavHostController = rememberNavController(),
    id: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.state2.value

    LaunchedEffect(Unit) { viewModel.getProductById(id) }

    Scaffold(
        topBar = {
            DetailsScreenTopAppBar(navController, scrollBehavior)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                is Resource.Error -> {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            action = {
                                IconButton(onClick = { viewModel.getProductById(id) }) {
                                    Icon(Icons.Default.Close, contentDescription = "Close")
                                }
                            },
                            content = { Text(text = state.message ?: "Unknown error") }
                        )
                    }
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val root = state.data
                    val product = root?.products?.find { it._id == id }
                    if (product != null) {
                        DetailContent(
                            product = product,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.DetailContent(
    product: Products,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val imageSlider = listOf(
        product.productImages.pi1,
        product.productImages.pi2,
        product.productImages.pi3,
        product.productImages.pi4
    )

    val pagerState = rememberPagerState(initialPage = 0)

    val culture = remember { mutableSetOf<String>() }
    val color = product.colors.joinToString()
    color.split(",").map { it.trim() }.forEach { culture.add(it) }

    val finalColorList = culture.toList()
    val selectedColor = if (finalColorList.isNotEmpty())
        remember { mutableStateOf(finalColorList.first()) }
    else
        remember { mutableStateOf("") }

    LaunchedEffect(Unit) { while (true) { yield(); delay(1000) } }
    val finalPrice = product.price?.div(100)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .safeDrawingPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column {
                HorizontalPager(
                    count = imageSlider.size,
                    state = pagerState,
                    modifier = Modifier
                        .height(290.dp)
                        .fillMaxWidth()
                ) { page ->
                    ImageSlider(
                        images = imageSlider,
                        modifier = Modifier.fillMaxSize(),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = product.name ?: "",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                    fontWeight = FontWeight.Bold
                ),
            )
            Text(
                text = "Price: ${finalPrice}$",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = product.description ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.poppins_light)),
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = product.specifications.toString(),  // Adjust how you access specifications if needed
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsScreenTopAppBar(
    navController: NavHostController = rememberNavController(),
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(
                onClick = { /** TODO: Handle favorite button click */ },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun SharedTransitionScope.ImageSlider(
    images: List<String?>,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    // Filter out null values and create a new list of non-null images
    val validImages = images.filterNotNull()

    // Handle the case where there are no valid images
    if (validImages.isEmpty()) {
        // Optionally show a placeholder or an empty state
        Text("No images available", modifier = modifier)
        return
    }

    val pagerState = rememberPagerState(initialPage = 0)

    Box(modifier = modifier) {
        HorizontalPager(
            count = validImages.size,
            state = pagerState,
            key = { validImages[it] }
        ) { index ->
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(validImages[index])
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedElement(
                        state = rememberSharedContentState(key = validImages[index]),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { initialRect, targetRect ->
                            spring(
                                dampingRatio = 0.8f,
                                stiffness = 380f
                            )
                        }
                    ),
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                activeColor = Color.White,
                inactiveColor = Color.Gray
            )
        }
    }
}