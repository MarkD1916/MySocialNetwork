package com.vmakd1916gmail.com.login_logout_register.repositories.auth

import androidx.lifecycle.LiveData
import com.vmakd1916gmail.com.login_logout_register.DB.MySocialNetworkDAO
import com.vmakd1916gmail.com.login_logout_register.models.Token
import com.vmakd1916gmail.com.login_logout_register.models.network.TokenResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.UserResponse
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import com.vmakd1916gmail.com.login_logout_register.other.getAuthDataFromServer
import com.vmakd1916gmail.com.login_logout_register.other.safeCall
import com.vmakd1916gmail.com.login_logout_register.api.AuthApi
import com.vmakd1916gmail.com.login_logout_register.other.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "AuthRepositoryImpl"

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) {



    suspend fun registerUser(userResponse: UserResponse):Resource<Response<UserResponse>> {

        return withContext(Dispatchers.IO) {
            safeCall {
                val call = authApi.registerUser(userResponse)
                val result = getAuthDataFromServer(call)
                Resource.Success(result)
            }
        }
    }


    suspend fun authUser(user: UserResponse): Resource<Response<TokenResponse>> {

        return withContext(Dispatchers.IO) {
            safeCall {
                val call = authApi.authUser(user)
                val result = getAuthDataFromServer(call)
                Resource.Success(result)
            }
        }
    }

}