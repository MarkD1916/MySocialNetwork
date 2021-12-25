package com.vmakd1916gmail.com.login_logout_register.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "post_table"
)
data class Post(
    @PrimaryKey val postId: Int,

    val author: String,
    val authorId: Int,

    val postTitle: String,
    val postTextContent: String,
    val postDate: String,
    val imageUrl: String,

    val likes_count: Int,
)
