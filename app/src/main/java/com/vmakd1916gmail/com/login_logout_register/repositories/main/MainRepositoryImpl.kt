package com.vmakd1916gmail.com.login_logout_register.repositories.main

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vmakd1916gmail.com.login_logout_register.DB.MySocialNetworkDatabase
import com.vmakd1916gmail.com.login_logout_register.DB.PostDAO
import com.vmakd1916gmail.com.login_logout_register.DB.RemotePostKeyDAO
import com.vmakd1916gmail.com.login_logout_register.api.PostApi
import com.vmakd1916gmail.com.login_logout_register.paging.datasource.PostPageSource
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import com.vmakd1916gmail.com.login_logout_register.other.safeCall
import com.vmakd1916gmail.com.login_logout_register.paging.remote_mediator.PostRemoteMediator
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

import javax.inject.Inject

private const val TAG = "MainRepositoryImpl"


class MainRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDAO: PostDAO,
    private val postKeyDAO: RemotePostKeyDAO,
    private val db: MySocialNetworkDatabase
) {


    @ExperimentalPagingApi
    suspend fun getPostFromNetwork(): Resource<Flow<PagingData<PostResponse>>> {

        return withContext(Dispatchers.IO) {

                    val call =
                        Pager(
                            config = PagingConfig(
                                pageSize = 3,
                                maxSize = 9
                            ),
                            remoteMediator = PostRemoteMediator(postApi, postDAO, postKeyDAO, db),
                        )
                        {
                            postDAO.pagingSource()
                        }.flow
//                    Log.d(TAG, "getPost: $call")
//                    val call = //postApi.getPost(1)
//                        Pager(
//                            config = PagingConfig(
//                                pageSize = 3,
//                                maxSize = 9,
//                                enablePlaceholders = true
//                            ),
//                            pagingSourceFactory = { PostPageSource(postApi) }
//                        ).flow
                    Resource.Success(call)



        }
    }


}