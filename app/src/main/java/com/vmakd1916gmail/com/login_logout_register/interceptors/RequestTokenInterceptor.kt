package com.vmakd1916gmail.com.login_logout_register.interceptors

import android.util.Log
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.Variables
import okhttp3.*
import javax.inject.Inject

private const val TAG = "RequestTokenInterceptor"

class RequestTokenInterceptor @Inject constructor(
    private val tokenPreferences: TokenPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

            val token = tokenPreferences.getStoredToken()
            return when (request.method()) {
                "GET" -> {
                    getResponse(token, request, chain)
                }
                "POST" -> {
                    getResponse(token, request, chain)
                }
                else -> {
                    val newRequest = request.newBuilder()
                        .build()
                    chain.proceed(newRequest)
                }
            }

    }


    private fun getResponse(token: String, request: Request, chain: Interceptor.Chain): Response {
        return if (token != "") {
            val newRequest =
                request.newBuilder().addHeader("Authorization", "Token $token")
                    .method(request.method(), request.body())
                    .build()
            chain.proceed(newRequest)
        } else {
            val newRequest =
                request.newBuilder()
                    .method(request.method(), request.body())
                    .build()
            chain.proceed(newRequest)
        }
    }
}



