package com.example.e_shop.catalog.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.catalog.domain.model.CategoryProduct
import com.example.e_shop.catalog.domain.use_case.GetCategoriesUseCase
import com.example.e_shop.catalog.domain.use_case.GetSortedProductsByCategoryUseCase
import com.example.e_shop.core.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSortedProductsByCategoryUseCase: GetSortedProductsByCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow<Resource<List<String>>>(Resource.Loading())
    val state: StateFlow<Resource<List<String>>> = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getCategoriesUseCase()
                }
                _state.update { Resource.Success(response) }
            } catch (e: Exception) {
                _state.update { Resource.Error(e.message ?: "An unknown error occurred") }
            }
        }
    }

    private val _categoryState = MutableStateFlow<Resource<List<CategoryProduct>>>(Resource.Loading())
    val categoryState: StateFlow<Resource<List<CategoryProduct>>> = _categoryState.asStateFlow()

    fun getSpecificCategoryProducts(category: String) {
        viewModelScope.launch {
            _categoryState.update { Resource.Loading() }
            try {
                val response = withContext(Dispatchers.IO) {
                    getSortedProductsByCategoryUseCase(category)
                }
                _categoryState.update { Resource.Success(response) }
            } catch (e: Exception) {
                _categoryState.update { Resource.Error(e.message ?: "An unknown error occurred") }
            }
        }
    }
}