package com.vmakd1916gmail.com.login_logout_register.models.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*


@Entity(
    tableName = "post_table"
)
data class PostResponse(
    @SerializedName("pk") @PrimaryKey val postId: Int,
    @SerializedName("post_title") val postTitle: String,
    @SerializedName("author_ro") val author: String,
    @SerializedName("author") val authorId: Int,
    @SerializedName("post_text_content") val postTextContent: String,
    @SerializedName("post_date") val postDate: String,
    @SerializedName("post_image") val imageUrl: String,
    @SerializedName("likes_count") val likes_count: Int,
    var page: Int?
)