package com.vmakd1916gmail.com.login_logout_register.models.network

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pageKey")
data class PageKey(
    @PrimaryKey val id: Int,
    val nextPageUrl: String?
)