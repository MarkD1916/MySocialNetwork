package com.vmakd1916gmail.com.login_logout_register.ui.auth.VM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmakd1916gmail.com.login_logout_register.models.local.User
import com.vmakd1916gmail.com.login_logout_register.models.network.*
import com.vmakd1916gmail.com.login_logout_register.other.Event
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Event<Resource<User>>>()
    val registerStatus:LiveData<Event<Resource<User>>> = _registerStatus

    fun registerUser(user: UserResponse) {
        _registerStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.registerUser(user)
            if (response.data!=null) {
                val user = createUser(response.data.body())
                _registerStatus.postValue(Event(Resource.Success(user)))
            }
            else {
                _registerStatus.postValue(Event(Resource.Error(response.message!!)))
            }

        }
    }

    private val _authStatus = MutableLiveData<Event<Resource<TokenResponse>>>()
    val authStatus:LiveData<Event<Resource<TokenResponse>>> = _authStatus

    fun authUser(user: UserResponse) {
        _authStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.authUser(user)
            if (response.data!=null) {
                val token = response.data.body()
                _authStatus.postValue(Event(Resource.Success(token!!)))
            }
            else {
                _authStatus.postValue(Event(Resource.Error(response.message!!)))
            }
        }
    }

    private val _logoutStatus = MutableLiveData<Event<Resource<Any>>>()
    val logoutStatus:LiveData<Event<Resource<Any>>> = _logoutStatus

    fun logoutUser(){
        _logoutStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.logoutUser()
            if (response.data!=null) {
                _logoutStatus.postValue(Event(Resource.Success(Unit)))
            }
            else {
                _logoutStatus.postValue(Event(Resource.Error(response.message!!)))
            }
        }
    }

    private fun createUser(user: UserResponse?): User {
        return User(
            UUID.randomUUID(),
            user?.name
        )
    }

    fun createUserResponse(userName: String, userPassword: String): UserResponse {
        return UserResponse(userName, userPassword)
    }

}