package com.example.e_shop.catalog.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.catalog.data.remote.categories
import com.example.e_shop.catalog.domain.use_case.GetSortedProductsByCategoryUseCase
import com.example.e_shop.core.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getSortedProductsByCategoryUseCase: GetSortedProductsByCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow<Resource<categories>>(Resource.Loading())
    val state: StateFlow<Resource<categories>> = _state.asStateFlow()

    fun getSortedProductsByCategory() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val response = withContext(Dispatchers.IO) {
                    getSortedProductsByCategoryUseCase()
                }
                _state.value = Resource.Success(response)
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}