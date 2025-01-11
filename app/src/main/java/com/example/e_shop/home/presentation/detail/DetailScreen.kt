package com.example.e_shop.home.presentation.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e_shop.R
import com.example.e_shop.home.domain.model.ProductImages
import com.example.e_shop.home.domain.model.Specifications
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalPagerApi::class
)
@Composable
fun SharedTransitionScope.DetailScreen(
    navController: NavHostController = rememberNavController(),
    id: String,
    name: String,
    price: Int,
    image: String,
    description: String,
    specs: Specifications,
    category: String,
    productImages: ProductImages,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val finalPrice = price.div(100)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(productImages.pi1,productImages.pi2,productImages.pi3,productImages.pi4)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .safeDrawingPadding()
                .padding(paddingValues),
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
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier
                            .height(114.dp)
                            .fillMaxWidth()
                    ) { page ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .graphicsLayer {
                                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }

                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                        ) {
                            GlideImage(
                                model = imageSlider[page],
                                contentDescription = name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .sharedElement(
                                        state = rememberSharedContentState(key = image),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { initialRect, targetRect ->
                                            spring(
                                                dampingRatio = 0.8f,
                                                stiffness = 380f
                                            )
                                        }
                                    )
                            )
                        }
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
                    text = name,
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
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.poppins_light)),
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = specs.cpu.toString(),
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,

                )
                Text(
                    text = "Product Category: $category",
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}