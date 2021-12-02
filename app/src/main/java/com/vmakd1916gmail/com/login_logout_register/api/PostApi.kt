package com.vmakd1916gmail.com.login_logout_register.api

import android.net.Uri
import com.vmakd1916gmail.com.login_logout_register.models.local.Post
import com.vmakd1916gmail.com.login_logout_register.models.network.PostPagedResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse
import com.vmakd1916gmail.com.login_logout_register.models.network.UserResponse
import com.vmakd1916gmail.com.login_logout_register.other.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {

    @POST("api/post/")
    suspend fun createPost(@Body post: Post?): Resource<Any>

    @GET("api/post/")
    suspend fun getPost(@Query("page") page: Int): Response<PostPagedResponse>
}