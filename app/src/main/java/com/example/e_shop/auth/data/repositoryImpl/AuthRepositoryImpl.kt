package com.example.e_shop.auth.data.repositoryImpl

import com.example.e_shop.auth.data.remote.AuthApi
import com.example.e_shop.auth.domain.model.Auth
import com.example.e_shop.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api : AuthApi): AuthRepository {

    override suspend fun registerUser(registerRequest: Auth): Auth {
        return api.registerUser(registerRequest)
    }
}