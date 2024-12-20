package com.example.e_shop.presentation.home.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e_shop.presentation.home.Resource
import com.example.e_shop.presentation.home.vm.HomeViewModel
import com.example.e_shop.presentation.navigation.Screens

@SuppressLint("AutoboxingStateCreation")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController()
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(true) { viewModel.getAllProducts() }
    Column(
        modifier = Modifier.fillMaxSize().padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (val state = viewModel.state.value) {
            is Resource.Loading -> { CircularProgressIndicator() }
            is Resource.Success -> { ProductItem(navController,viewModel) }
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.data?.products?.size ?: 0) { index ->
            state.data?.apply {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            navController.navigate(
                                Screens.Details(
                                    id = products[index].Id.toString(),
                                    name = products[index].name.toString(),
                                    price = products[index].price!!,
                                    image = products[index].image.toString(),
                                    description = products[index].description.toString(),
                                    specs = products[index].specifications.toString()
                                )
                            )
                        }
                ) {
                    GlideImage(
                        model = products[index].image,
                        contentDescription = products[index].name,
                        modifier = Modifier.padding(8.dp).height(150.dp)
                    )
                    Text(
                        text = products[index].name ?: "",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}