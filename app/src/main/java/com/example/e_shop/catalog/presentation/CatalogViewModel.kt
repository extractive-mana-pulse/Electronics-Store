package com.example.e_shop.catalog.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.core.resource.Resource
import com.example.e_shop.home.domain.model.Root
import com.example.e_shop.home.domain.use_case.GetSortedProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(val getSortedProductsByCategoryUseCase: GetSortedProductsByCategoryUseCase): ViewModel() {

    private val _state = mutableStateOf<Resource<Root>>(Resource.Loading())
    val state: State<Resource<Root>> = _state

    fun getSortedProductsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = getSortedProductsByCategoryUseCase(category)
                _state.value = Resource.Success(products)
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}