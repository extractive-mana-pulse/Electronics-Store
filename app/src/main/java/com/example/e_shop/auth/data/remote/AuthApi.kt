package com.example.e_shop.auth.data.remote

import com.example.e_shop.auth.domain.model.Auth
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST
    suspend fun registerUser(@Body registerRequest: Auth): Auth

}