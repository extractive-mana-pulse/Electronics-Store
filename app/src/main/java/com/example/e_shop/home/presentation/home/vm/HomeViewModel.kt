package com.example.e_shop.home.presentation.home.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.use_case.GetAllProductsUseCase
import com.example.e_shop.home.domain.use_case.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf<Resource<Root>>(Resource.Loading())
    val state: State<Resource<Root>> = _state

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            _value.value = Resource.Loading()
            try {
                val response = getProductByIdUseCase(id)
                if (true)
                    _value.value = Resource.Success(response)
                 else
                    _value.value = Resource.Error("Data not found")
            } catch (e: Exception) {
                _value.value = Resource.Error("Error: ${e.message}")
            }
        }
    }
}