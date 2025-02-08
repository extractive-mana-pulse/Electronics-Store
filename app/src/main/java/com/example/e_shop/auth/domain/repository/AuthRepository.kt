package com.example.e_shop.auth.domain.repository

import com.example.e_shop.auth.domain.model.Auth

interface AuthRepository {
    suspend fun registerUser(registerRequest: Auth): Auth
}