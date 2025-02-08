package com.example.e_shop.auth.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.example.e_shop.auth.domain.model.Auth
import com.example.e_shop.auth.domain.repository.AuthRepository
import com.example.e_shop.core.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Auth>>(Resource.Loading())
    val registerState: StateFlow<Resource<Auth>> = _registerState.asStateFlow()

    fun authUser(registerRequest: Auth) {
        _registerState.value = Resource.Loading()
        viewModelScope.launch {
            supervisorScope {
                try {
                    val response = repository.registerUser(registerRequest)
                    _registerState.value = Resource.Success(response)
                } catch (e : HttpException) {
                    _registerState.value = Resource.Error(e.message ?: "An unknown error occurred")
                }
            }
        }
    }
}