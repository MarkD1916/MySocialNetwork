package com.vmakd1916gmail.com.login_logout_register.models.network

import com.google.gson.annotations.SerializedName

data class PostPagedResponse(
    @SerializedName("count")val count: Int,
    @SerializedName("next")val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") val results: List<PostResponse> = listOf()
)

//data class PageInfo(
//
//)

data class PostResponse(
    @SerializedName("pk") val postId: Int,
    @SerializedName("post_title") val postTitle: String,
    @SerializedName("author_ro") val author: String,
    @SerializedName("author") val authorId: Int,
    @SerializedName("post_text_content") val postTextContent: String,
    @SerializedName("post_date") val postDate: String,
    @SerializedName("post_image") val imageUrl: String,
    @SerializedName("likes_count") val likes_count: Int
)