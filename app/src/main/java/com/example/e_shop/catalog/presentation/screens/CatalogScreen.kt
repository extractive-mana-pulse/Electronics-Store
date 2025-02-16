package com.example.e_shop.catalog.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.catalog.data.remote.categories
import com.example.e_shop.catalog.presentation.vm.CatalogViewModel
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.core.util.CircularLoadingProgress
import com.example.e_shop.navigation.screens.HomeScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    val viewModel: CatalogViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            CatalogTopAppBar(scrollBehavior)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val result = state.value) {
                is Resource.Loading -> CircularLoadingProgress()
                is Resource.Success -> {
                    result.data?.let { categories ->
                        CategoriesScreen(
                            categoriesList = categories,
                            onCategoryClick = { category ->
                                navController.navigate(
                                    HomeScreens.Category(
                                        category
                                    )
                                )
                            }
                        )
                    }
                }
                is Resource.Error -> {
                    toastMessage(
                        context = context,
                        message = state.value.message.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    categoriesList: categories,
    onCategoryClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        items(categoriesList) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCategoryClick(category) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = FontFamily(Font(R.font.gabarito_bold))
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CatalogTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    MediumTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.catalogTopAppBarTitle),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.gabarito_regular))
                )
            )
        },
        scrollBehavior = scrollBehavior
    )
}