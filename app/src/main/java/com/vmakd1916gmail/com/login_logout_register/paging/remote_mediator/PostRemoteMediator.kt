package com.vmakd1916gmail.com.login_logout_register.paging.remote_mediator

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.vmakd1916gmail.com.login_logout_register.DB.PostDAO
import com.vmakd1916gmail.com.login_logout_register.DB.RemotePostKeyDAO
import com.vmakd1916gmail.com.login_logout_register.api.PostApi
import com.vmakd1916gmail.com.login_logout_register.models.network.PageKey
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator (
    private val postApi: PostApi,
    private val postDAO: PostDAO,
    private val keyDAO: RemotePostKeyDAO
) : RemoteMediator<Int, PostResponse>() {
    private val TAG = "PostRemoteMediator"
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostResponse>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    val remoteKey: PageKey? =
                        if (lastItem?.postId != null) {
                            keyDAO.getNextPageKey(lastItem.postId)
                        } else null


                    if (remoteKey?.nextPageUrl == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    val uri = Uri.parse(remoteKey.nextPageUrl)
                    val nextPageQuery = uri.getQueryParameter("page")
                    nextPageQuery?.toInt()
                }
            }

            val response = postApi.getPost(loadKey ?: 1)
            val resBody = response.body()
            val pageInfoNext = resBody?.next
            val data = resBody?.results

            if (loadType == LoadType.REFRESH) {
                Log.d(TAG, "load: $loadType")
//                if ()
//                postDAO.clearAll()
//                keyDAO.clearAll()
            }
            data?.forEach {
                it.page = loadKey
                keyDAO.insertOrReplace(PageKey(it.postId, pageInfoNext))
            }
            data?.let {
                Log.d(TAG, "load: $it")
                postDAO.insertAll(it) }



            MediatorResult.Success(
                endOfPaginationReached = resBody?.next == null
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


}
