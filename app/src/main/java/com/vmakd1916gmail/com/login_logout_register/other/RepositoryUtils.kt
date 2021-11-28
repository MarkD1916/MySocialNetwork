package com.vmakd1916gmail.com.login_logout_register.other

import retrofit2.Response

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error occurred")
    }
}

fun <T> getAuthDataFromServer(response: Response<T>): Response<T> {

    if (response.isSuccessful) {
        return response
    } else {
        throw Exception(response.message())
    }

}
