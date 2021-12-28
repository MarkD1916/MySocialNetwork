package com.vmakd1916gmail.com.login_logout_register.paging.remote_mediator

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vmakd1916gmail.com.login_logout_register.DB.MySocialNetworkDatabase
import com.vmakd1916gmail.com.login_logout_register.DB.PostDAO
import com.vmakd1916gmail.com.login_logout_register.DB.RemotePostKeyDAO
import com.vmakd1916gmail.com.login_logout_register.api.PostApi
import com.vmakd1916gmail.com.login_logout_register.models.network.PageKey
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.repositories.auth.Variables
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postApi: PostApi,
    private val postDAO: PostDAO,
    private val keyDAO: RemotePostKeyDAO,
    private val db: MySocialNetworkDatabase
) : RemoteMediator<Int, PostResponse>() {
    private val TAG = "PostRemoteMediator"
    private val initialPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostResponse>
    ): MediatorResult {
        return try {

            //Log.d(TAG, "state: $state")

            Log.d(TAG, "loadType: $loadType")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    Log.d(TAG, "lastItem: $lastItem")

                    val remoteKey: PageKey? =
                        if (lastItem?.postId != null) {
                            Log.d(TAG, "keyDAO: ${keyDAO.getNextPageKey(lastItem.postId)}")
                            keyDAO.getNextPageKey(lastItem.postId)

                        } else null



                    if (remoteKey?.nextPageUrl == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    val uri = Uri.parse(remoteKey.nextPageUrl)
                    //Log.d(TAG, "uri: $uri")
                    val nextPageQuery = uri.getQueryParameter("page")
                    //Log.d(TAG, "nextPageQuery: $nextPageQuery")
                    val nextPageInt = nextPageQuery?.toInt()
                    //Log.d(TAG, "nextPageQuery: ${nextPageQuery?.toInt()}")
                    nextPageInt
                }
            }

            //Log.d(TAG, "loadKey: $loadKey")
            val response = postApi.getPost(loadKey ?: 1)
            val resBody = response.body()
            val pageInfoNext = resBody?.next
            val data = resBody?.results

            if (loadType == LoadType.REFRESH) {
                if (Variables.isNetworkConnected) {
                    postDAO.clearAll()
                    keyDAO.clearAll()
                }
            }
            db.withTransaction {
                data?.forEach {
                    it.page = loadKey
                    keyDAO.insertOrReplace(PageKey(it.postId, pageInfoNext))
                }
                data?.let {
                    postDAO.insertAll(it)
                }
            }
            //Log.d(TAG, "resBody: ${resBody?.next}")


            MediatorResult.Success(
                endOfPaginationReached = resBody?.next == null
            )

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


}
