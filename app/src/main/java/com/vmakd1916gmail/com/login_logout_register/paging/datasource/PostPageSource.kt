package com.vmakd1916gmail.com.login_logout_register.paging.datasource

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vmakd1916gmail.com.login_logout_register.DB.PostDAO
import com.vmakd1916gmail.com.login_logout_register.api.PostApi
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse

class PostPageSource(private val postApi: PostApi) :
    PagingSource<Int, PostResponse>() {
    private val TAG = "PostPageSource"
    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, PostResponse>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostResponse> {
        val pageNumber = params.key ?: 1
        val response = postApi.getPost(pageNumber)
        val pagedResponse = response.body()

        val data = pagedResponse?.results


        var nextPageNumber: Int? = null
        if (pagedResponse?.next != null) {
            val uri = Uri.parse(pagedResponse.next)
            val nextPageQuery = uri.getQueryParameter("page")
            nextPageNumber = nextPageQuery?.toInt()
        }

        return LoadResult.Page(
            data = data.orEmpty(),
            prevKey = if (pageNumber == 1) null else pageNumber - 1,
            nextKey = nextPageNumber
        )
    }
}