package com.vmakd1916gmail.com.login_logout_register.api

import com.vmakd1916gmail.com.login_logout_register.models.network.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/users/")
    suspend fun registerUser(@Body user: UserResponse?): Response<UserResponse>

    @POST("auth/token/login/")
    suspend fun authUser(@Body user: UserResponse): Response<TokenResponse>

    @POST("auth/token/logout/")
    suspend fun logoutUser(): Response<Any>

}