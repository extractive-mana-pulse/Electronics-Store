package com.example.e_shop.home.presentation.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.ProductItem
import com.example.e_shop.home.domain.use_case.GetAllProductsUseCase
import com.example.e_shop.home.domain.use_case.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            getAllProducts()
            delay(1500L)
            _isReady.value = true
        }
    }

    private val _state = mutableStateOf<Resource<List<ProductItem>>>(Resource.Loading())
    val state: State<Resource<List<ProductItem>>> = _state

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

    private val productByIdState = mutableStateOf<Resource<ProductItem>>(Resource.Loading())
    val productById: State<Resource<ProductItem>> = productByIdState

    fun getProductById(id: Int) {
        viewModelScope.launch {
            productByIdState.value = Resource.Loading()
            try {
                val response = getProductByIdUseCase(id)
                if (true)
                    productByIdState.value = Resource.Success(response)
                 else
                    productByIdState.value = Resource.Error("Data not found")
            } catch (e: Exception) {
                productByIdState.value = Resource.Error("Error: ${e.message}")
            }
        }
    }
}