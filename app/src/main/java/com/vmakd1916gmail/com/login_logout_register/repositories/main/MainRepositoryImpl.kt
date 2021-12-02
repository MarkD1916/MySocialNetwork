package com.vmakd1916gmail.com.login_logout_register.repositories.main

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vmakd1916gmail.com.login_logout_register.api.PostApi
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import com.vmakd1916gmail.com.login_logout_register.other.getAuthDataFromServer
import com.vmakd1916gmail.com.login_logout_register.other.safeCall
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

import javax.inject.Inject

private const val TAG = "MainRepositoryImpl"

@ActivityScoped
class MainRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) {

    suspend fun getPost(): Resource<Flow<PagingData<PostResponse>>> {

        return withContext(Dispatchers.IO) {
            safeCall {
                val call = //postApi.getPost(1)
                    Pager(
                        config = PagingConfig(
                            pageSize = 3,
                            maxSize = 9,
                            enablePlaceholders = true
                        ),
                        pagingSourceFactory = { PostPageSource(postApi) }
                    ).flow
                Log.d(TAG, "getPost: $call")
                //val result = getAuthDataFromServer(call)
                Resource.Success(call)
            }

        }
    }

}