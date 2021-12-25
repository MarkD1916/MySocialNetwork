package com.vmakd1916gmail.com.login_logout_register.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vmakd1916gmail.com.login_logout_register.models.network.PageKey
import com.vmakd1916gmail.com.login_logout_register.models.network.PostResponse

@Database(entities = [PostResponse::class, PageKey::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class MySocialNetworkDatabase : RoomDatabase() {
    abstract fun PostDAO(): PostDAO
    abstract fun RemotePostKeyDAO(): RemotePostKeyDAO
}