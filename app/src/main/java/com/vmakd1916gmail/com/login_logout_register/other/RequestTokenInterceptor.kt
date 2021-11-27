package com.vmakd1916gmail.com.login_logout_register.other

import android.util.Log
import com.vmakd1916gmail.com.login_logout_register.api.AuthApi
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
            when (request.method()) {
                "GET" -> {
                    if (token != "") {
                        val newRequest =
                            request.newBuilder().addHeader("Authorization", "Token $token")
                                .method(request.method(), request.body())
                                .build()
                        return chain.proceed(newRequest)
                    } else {
                        val newRequest =
                            request.newBuilder()
                                .method(request.method(), request.body())
                                .build()
                        return chain.proceed(newRequest)
                    }
                }
                else -> {
                    val newRequest = request.newBuilder()
                        .build()
                    return chain.proceed(newRequest)
                }
            }
        }
        catch (e:java.lang.Exception){
            if (!Variables.isNetworkConnected) {
                safeCall<Any> {
                    throw Exception("No Internet connection")
                }
                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message("No Internet connection")
                    .body(ResponseBody.create(null, "{${e}}")).build()
            }
            else{
                safeCall<Any> {
                    throw Exception(e.message)
                }
                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message(e.message)
                    .body(ResponseBody.create(null, "{${e}}")).build()
            }

        }

        }
    }



