package com.vmakd1916gmail.com.login_logout_register.other

import android.util.Log
import androidx.paging.PagingData
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.Variables
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {

    return try {
        action()
    } catch (e: Exception) {
        Log.d( "safeCall", "safeCall: $e")
        if (!Variables.isNetworkConnected) {
            Resource.Error("No Internet Connection")
        }
        else {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}

fun <T> getAuthDataFromServer(response: Response<T>): Response<T> {

    if (response.isSuccessful) {
        return response
    } else {
        throw Exception(response.message())
    }
}

