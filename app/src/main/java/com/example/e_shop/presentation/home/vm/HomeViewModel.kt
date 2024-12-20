package com.example.e_shop.presentation.home.vm

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.domain.model.Root
import com.example.e_shop.domain.use_case.GetAllProductsUseCase
import com.example.e_shop.presentation.home.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetAllProductsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Root>>(Resource.Loading())
    val state: State<Resource<Root>> = _state

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "Starting getAllProducts")
                val products = useCase()
                Log.d("HomeViewModel", "Success: products = $products")
                Log.d("HomeViewModel", "Products size: ${products.products.size}")
                _state.value = Resource.Success(products)
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}