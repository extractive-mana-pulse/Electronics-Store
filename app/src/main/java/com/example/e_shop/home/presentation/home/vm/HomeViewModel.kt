package com.example.e_shop.home.presentation.home.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.Products
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.use_case.GetAllProductsUseCase
import com.example.e_shop.home.domain.use_case.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Root>>(Resource.Loading())
    val state: State<Resource<Root>> = _state

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val products = getAllProductsUseCase()
                _state.value = Resource.Success(products)
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    private val _value = mutableStateOf<Resource<Root>>(Resource.Loading())
    val state2: State<Resource<Root>> = _value

    fun getProductById(id: String) {
        viewModelScope.launch {
            // Set loading state
            _value.value = Resource.Loading()
            try {
                // Fetch the product from the repository
                val response = getProductByIdUseCase(id)

                Log.d("ViewModel", "Response: $response")
                if (true) {
                    _value.value = Resource.Success(response)
                } else {
                    _value.value = Resource.Error("No data found")
                }
            } catch (e: Exception) {
                // Handle any exceptions
                _value.value = Resource.Error("Error: ${e.message}")
            }
        }
    }
}