package com.example.e_shop.auth.domain.model

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
