package com.example.e_shop.presentation.home.screen

import CategoriesSection
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e_shop.R
import com.example.e_shop.domain.model.Root
import com.example.e_shop.presentation.home.Resource
import com.example.e_shop.presentation.home.vm.HomeViewModel
import com.example.e_shop.presentation.navigation.Screens

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
    Column(
        modifier = Modifier.fillMaxSize(),
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
            // TODO
        }
        item {
            CategoriesSection()
        }
        item {
            Extracted(state, categories, navController,animatedVisibilityScope)
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchableList() {
//    val textFieldState = rememberTextFieldState()
//    var expanded by rememberSaveable { mutableStateOf(false) }
//
//    Box(Modifier.fillMaxSize()) {
//        SearchBar(
//            modifier = Modifier.align(Alignment.TopCenter),
//            query = textFieldState.text,
//            onQueryChange = { textFieldState.text = it },
//            onSearch = { expanded = false },
//            active = expanded,
//            onActiveChange = { },
//            placeholder = { Text("Search") },
//            expanded = expanded,
//            onExpandedChange = { expanded = it }
//        ) {
//            Column(Modifier.verticalScroll(rememberScrollState())) {
//                repeat(4) { idx ->
//                    val resultText = "Suggestion $idx"
//                    ListItem(
//                        headlineContent = { Text(resultText) },
//                        supportingContent = { Text("Additional info") },
//                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
//                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
//                        modifier = Modifier
//                            .clickable {
//                                textFieldState.setTextAndPlaceCursorAtEnd(resultText)
//                                expanded = false
//                            }
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        }
//
//        LazyColumn(
//            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier
//        ) {
//            val list = List(100) { "Text $it" }
//            items(count = list.size) { index ->
//                Text(
//                    text = list[index],
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                )
//            }
//        }
//    }
//}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalSharedTransitionApi::class)
private fun SharedTransitionScope.Extracted(
    state: Resource<Root>,
    categories: MutableSet<String>,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    LazyRow {
        itemsIndexed(state.data?.products ?: emptyList()) { index, product ->
            product.let {
                categories.add(it.category.toString())
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
                                    specs = it.specifications.toString(),
                                    category = it.category.toString()
                                )
                            )
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        GlideImage(
                            model = it.image,
                            contentDescription = it.name,
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
                            text = it.name ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.poppins_black)),
                                fontWeight = FontWeight.Light
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$${it.price?.div(100).toString()}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght)),
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}