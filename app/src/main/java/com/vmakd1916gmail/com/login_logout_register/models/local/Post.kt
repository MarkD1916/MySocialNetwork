package com.vmakd1916gmail.com.login_logout_register.models.local

import com.google.gson.annotations.SerializedName
import java.util.*


data class Post(
    val id: UUID? = UUID.randomUUID(),
    @SerializedName("author") val authorUid: Int? = null,
    @SerializedName("post_text_content")val text:Any,
    @SerializedName("post_image")val imageUrl:Any,
    @SerializedName("post_date")val date: Any
)
