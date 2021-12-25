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

        try {
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
        } catch (e: java.lang.Exception) {
            if (!Variables.isNetworkConnected) {
                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message("No Internet connection")
                    .body(ResponseBody.create(null, "{${e}}")).build()
            } else {
                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message(e.message)
                    .body(ResponseBody.create(null, "{${e}}")).build()
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


