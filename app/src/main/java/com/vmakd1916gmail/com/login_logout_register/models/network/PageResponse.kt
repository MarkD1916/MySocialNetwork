package com.vmakd1916gmail.com.login_logout_register.models.network

import com.google.gson.annotations.SerializedName

data class PagedResponse<T>(
    @SerializedName("count")val count: Int,
    @SerializedName("next")val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") val results: List<T> = listOf()
)
